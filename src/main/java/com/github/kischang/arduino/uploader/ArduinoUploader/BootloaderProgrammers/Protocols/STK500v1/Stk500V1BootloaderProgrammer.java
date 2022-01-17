package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1;

import com.github.kischang.arduino.uploader.ArduinoUploader.ArduinoUploaderException;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.ArduinoBootloaderProgrammer;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.IRequest;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.IMcu;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.IMemory;
import com.github.kischang.arduino.uploader.CSharpStyle.BitConverter;
import com.github.kischang.arduino.uploader.UsbSerialHelper.ISerialPortStream;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.*;

import com.github.kischang.arduino.uploader.ArduinoUploader.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;

public class Stk500V1BootloaderProgrammer<E extends ISerialPortStream> extends ArduinoBootloaderProgrammer<E> {
	public Stk500V1BootloaderProgrammer(com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.SerialPortConfig serialPortConfig, IMcu mcu) {
		super(serialPortConfig, mcu);
	}

	@Override
	public void EstablishSync() {
		final int maxRetries = 256;
		int retryCounter = 0;
		while (retryCounter++ < maxRetries) {
			 getSerialPort().DiscardInBuffer();
			Send(new GetSyncRequest());
			GetSyncResponse getSyncResponse = new GetSyncResponse();
			GetSyncResponse result = Receive(getSyncResponse);
			if (result == null) {
				continue;
			}
			if (result.getIsInSync()) {
				break;
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (retryCounter == maxRetries) {
			throw new ArduinoUploaderException(
					String.format("Unable to establish sync after %1$s retries.", maxRetries));
		}

		retryCounter = 0;
		while (retryCounter++ < maxRetries) {
			int nextByte = ReceiveNext();
			if (nextByte == Constants.RespStkOk) {
				break;
			}
		}
		if (retryCounter == maxRetries) {
			throw new ArduinoUploaderException("Unable to establish sync.");
		}
	}

	protected final void SendWithSyncRetry(IRequest request) {

		byte nextByte;
		while (true) {
			Send(request);
			nextByte = (byte) ReceiveNext();
			if (nextByte == Constants.RespStkNosync) {
				EstablishSync();
				continue;
			}
			break;
		}
		if (nextByte != Constants.RespStkInsync) {
			throw new ArduinoUploaderException(String.format(
					"Unable to aqcuire sync in SendWithSyncRetry for request of type %1$s!", request.getClass()));
		}
	}

	@Override
	public void CheckDeviceSignature() {
		if (getLogger() != null)
			getLogger().debug("Expecting to find '${}'...", getMcu().getDeviceSignature());
		SendWithSyncRetry(new ReadSignatureRequest());
		// ReadSignatureResponse response = this.<ReadSignatureResponse>Receive(4);
		ReadSignatureResponse getSyncResponse = new ReadSignatureResponse();
		ReadSignatureResponse response = Receive(getSyncResponse, 4);

		if (response == null || !response.getIsCorrectResponse()) {
			throw new ArduinoUploaderException("Unable to check device signature!");
		}

		byte[] signature = response.getSignature();
		if (!BitConverter.toString(signature).equalsIgnoreCase(getMcu().getDeviceSignature())) {
			throw new ArduinoUploaderException(
					String.format("Unexpected device signature - found '%1$s'- expected '%2$s'.",
							BitConverter.toString(signature), getMcu().getDeviceSignature()));
		}
	}

	@Override
	public void InitializeDevice() {
		int majorVersion = GetParameterValue(Constants.ParmStkSwMajor);
		int minorVersion = GetParameterValue(Constants.ParmStkSwMinor);
		if (getLogger() != null)
			getLogger().info("Retrieved software version: ${}.${} .", majorVersion, minorVersion);

		if (getLogger() != null)
			getLogger().info("Setting device programming parameters...");
		SendWithSyncRetry(new SetDeviceProgrammingParametersRequest((com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Mcu) getMcu()));
		int nextByte = ReceiveNext();

		if (nextByte != Constants.RespStkOk) {
			throw new ArduinoUploaderException("Unable to set device programming parameters!");
		}
	}

	@Override
	public void EnableProgrammingMode() {
		SendWithSyncRetry(new EnableProgrammingModeRequest());
		int nextByte = ReceiveNext();
		if (nextByte == Constants.RespStkOk) {
			return;
		}
		if (nextByte == Constants.RespStkNodevice || nextByte == Constants.RespStkFailed) {
			throw new ArduinoUploaderException("Unable to enable programming mode on the device!");
		}
	}

	@Override
	public void LeaveProgrammingMode() {
		SendWithSyncRetry(new LeaveProgrammingModeRequest());
		int nextByte = ReceiveNext();
		if (nextByte == Constants.RespStkOk) {
			return;
		}
		if (nextByte == Constants.RespStkNodevice || nextByte == Constants.RespStkFailed) {
			throw new ArduinoUploaderException("Unable to leave programming mode on the device!");
		}
	}

	private int GetParameterValue(byte param) {
		if (getLogger() != null)
			getLogger().trace("Retrieving parameter '${}'...", param);
		SendWithSyncRetry(new GetParameterRequest(param));
		int nextByte = ReceiveNext();
		int paramValue = (int) nextByte;
		nextByte = ReceiveNext();

		if (nextByte == Constants.RespStkFailed) {
			throw new ArduinoUploaderException(String.format("Retrieving parameter '%1$s' failed!", param));
		}

		if (nextByte != Constants.RespStkOk) {
			throw new ArduinoUploaderException(
					String.format("General protocol error while retrieving parameter '%1$s'.", param));
		}

		return paramValue;
	}

	@Override
	public void ExecuteWritePage(IMemory memory, int offset, byte[] bytes) {
		SendWithSyncRetry(new ExecuteProgramPageRequest(memory, bytes));
		int nextByte = ReceiveNext();
		if (nextByte == Constants.RespStkOk) {
			return;
		}
		throw new ArduinoUploaderException(String.format("Write at offset %1$s failed!", offset));
	}

	@Override
	public byte[] ExecuteReadPage(IMemory memory) {
		int pageSize = memory.getPageSize();
		SendWithSyncRetry(new ExecuteReadPageRequest(memory.getType(), pageSize));

		byte[] bytes = ReceiveNext(pageSize);
		if (bytes == null) {
			throw new ArduinoUploaderException("Execute read page failed!");
		}

		int nextByte = ReceiveNext();
		if (nextByte == Constants.RespStkOk) {
			return bytes;
		}
		throw new ArduinoUploaderException("Execute read page failed!");
	}

	@Override
	public void LoadAddress(IMemory memory, int addr) {
		if (getLogger() != null)
			getLogger().trace("Sending load address request: ${}.", addr);

		addr = addr >> 1;
		SendWithSyncRetry(new LoadAddressRequest(addr));
		int result = ReceiveNext();
		if (result == Constants.RespStkOk) {
			return;
		}
		throw new ArduinoUploaderException(String.format("LoadAddress failed with result %1$s!", result));
	}
}