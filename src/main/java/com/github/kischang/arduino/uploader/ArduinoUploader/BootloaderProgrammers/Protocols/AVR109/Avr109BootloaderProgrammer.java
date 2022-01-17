package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109;

import com.github.kischang.arduino.uploader.ArduinoUploader.ArduinoUploaderException;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.ArduinoBootloaderProgrammer;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.WaitHelper;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.IMcu;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.IMemory;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.MemoryType;
import com.github.kischang.arduino.uploader.CSharpStyle.BitConverter;
import com.github.kischang.arduino.uploader.UsbSerialHelper.ISerialPortStream;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages.*;

import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.*;
import csharpstyle.StringHelper;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Avr109BootloaderProgrammer<E extends ISerialPortStream> extends ArduinoBootloaderProgrammer<E> {
	public Avr109BootloaderProgrammer(com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.SerialPortConfig serialPortConfig, IMcu mcu) {
		super(serialPortConfig, mcu);
	}

	@Override
	public void Close() {
		try {
			String currentPort = getSerialPort().getPortName();
			if (getLogger() != null)
				getLogger().info("Closing ${}...", currentPort);
			getSerialPort().close();
			if (getLogger() != null)
				getLogger().info("Waiting for virtual port ${} to disappear...", currentPort);
			final int timeoutVirtualPointDisappearance = 10000;
			final int virtualPortDisappearanceInterval = 100;
			List<String> portNames = new ArrayList<>();
			for (String port : getSerialPort().getPortNames()) {
				portNames.add(port);

			}
			String result = WaitHelper.WaitFor(timeoutVirtualPointDisappearance, virtualPortDisappearanceInterval,
					() -> portNames.contains(currentPort) ? null : currentPort,
					(i, item, interval) -> item == null ? String.format("T+%1$s - Port still present...", i * interval)
							: String.format("T+%1$s - Port disappeared: %2$s!", i * interval, item));

			if (result == null) {
				if (getLogger() != null)
					getLogger().warn("Virtual COM port ${} was still present after {timeoutVirtualPointDisappearance} ms!", currentPort);
			}
		} catch (RuntimeException ex) {
			throw new ArduinoUploaderException(
					String.format("Exception during close of the programmer: '%1$s'.", ex.getMessage()));
		}
	}

	@Override
	public void CheckDeviceSignature() {
		if (getLogger() != null)
			getLogger().debug("Expecting to find '${}'...", getMcu().getDeviceSignature());
		Send(new ReadSignatureBytesRequest());
//        ReadSignatureBytesResponse response = this.<ReadSignatureBytesResponse>Receive(3);
		ReadSignatureBytesResponse readSignatureBytesResponse = new ReadSignatureBytesResponse();
		ReadSignatureBytesResponse response = Receive(readSignatureBytesResponse, 3);
		if (response == null) {
			throw new ArduinoUploaderException("Unable to check device signature!");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var signature = response.Signature;
		byte[] signature = response.getSignature();
		if (!BitConverter.toString(signature).equals(getMcu().getDeviceSignature())) {
			throw new ArduinoUploaderException(
					String.format("Unexpected device signature - found '%1$s'", BitConverter.toString(signature))
							+ String.format("- expected '%1$s'.", getMcu().getDeviceSignature()));
		}
	}

	@Override
	public void InitializeDevice() {
		Send(new ReturnSoftwareIdentifierRequest());
//        ReturnSoftwareIdentifierResponse softIdResponse = this.<ReturnSoftwareIdentifierResponse>Receive(7);
		ReturnSoftwareIdentifierResponse returnSoftwareIdentifierResponse = new ReturnSoftwareIdentifierResponse();
		ReturnSoftwareIdentifierResponse softIdResponse = Receive(returnSoftwareIdentifierResponse, 7);
		if (softIdResponse == null) {
			throw new ArduinoUploaderException("Unable to retrieve software identifier!");
		}

		String identifier = null;
		identifier = new String(softIdResponse.getBytes(), 0, softIdResponse.getBytes().length, StandardCharsets.US_ASCII);
		if (getLogger() != null)
			getLogger().info("Software identifier: '${}'", identifier);

		Send(new ReturnSoftwareVersionRequest());
//        ReturnSoftwareVersionResponse softVersionResponse = this.<ReturnSoftwareVersionResponse>Receive(2);
		ReturnSoftwareVersionResponse returnSoftwareVersionResponse = new ReturnSoftwareVersionResponse();
		ReturnSoftwareVersionResponse softVersionResponse = Receive(returnSoftwareVersionResponse, 2);
		if (softVersionResponse == null) {
			throw new ArduinoUploaderException("Unable to retrieve software version!");
		}

		if (getLogger() != null)
			getLogger().info("Software Version: ${}, ${}", softVersionResponse.getMajorVersion(),
					softVersionResponse.getMinorVersion());

		Send(new ReturnProgrammerTypeRequest());
//        ReturnProgrammerTypeResponse progTypeResponse = this.<ReturnProgrammerTypeResponse>Receive();
		ReturnProgrammerTypeResponse returnProgrammerTypeResponse = new ReturnProgrammerTypeResponse();
		ReturnProgrammerTypeResponse progTypeResponse = Receive(returnProgrammerTypeResponse);
		if (progTypeResponse == null) {
			throw new ArduinoUploaderException("Unable to retrieve programmer type!");
		}

		if (getLogger() != null)
			getLogger().info("Programmer type: ${}.", progTypeResponse.getProgrammerType());

		Send(new CheckBlockSupportRequest());
//        CheckBlockSupportResponse checkBlockResponse = this.<CheckBlockSupportResponse>Receive(3);
		CheckBlockSupportResponse checkBlockSupportResponse = new CheckBlockSupportResponse();
		CheckBlockSupportResponse checkBlockResponse = Receive(checkBlockSupportResponse, 3);

		if (checkBlockResponse == null) {
			throw new ArduinoUploaderException("Unable to retrieve block support!");
		}
		if (!checkBlockResponse.getHasBlockSupport()) {
			throw new ArduinoUploaderException("Block support is not supported!");
		}

		if (getLogger() != null)
			getLogger()
					.info("Block support - buffer size ${} bytes.", checkBlockResponse.getBufferSize());

		Send(new ReturnSupportedDeviceCodesRequest());

		ArrayList<Byte> devices = new ArrayList<Byte>();
		do {
			byte nextByte = (byte) ReceiveNext();
			if (nextByte != Constants.Null) {
				devices.add(nextByte);
			} else {
				break;
			}
		} while (true);

		String[] devicesStr = new String[devices.size()];
		for (int i = 0; i < devices.size(); i++) {
			devicesStr[i] = String.valueOf(devices.get(i));
		}
		String supportedDevices = StringHelper.join("-", devicesStr);
		if (getLogger() != null)
			getLogger().info("Supported devices: ${}.", supportedDevices);

		byte devCode = getMcu().getDeviceCode();
		if (!devices.contains(devCode)) {
			throw new ArduinoUploaderException(
					String.format("Device %1$s not in supported list of devices: %2$s!", devCode, supportedDevices));
		}

		if (getLogger() != null)
			getLogger().info("Selecting device type '${}'...", devCode);
		Send(new SelectDeviceTypeRequest(devCode));
		int response = ReceiveNext();
		if (response != Constants.CarriageReturn) {
			throw new ArduinoUploaderException("Unable to execute select device type command!");
		}
	}

	@Override
	public void EnableProgrammingMode() {
		Send(new EnterProgrammingModeRequest());
		int response = ReceiveNext();
		if (response != Constants.CarriageReturn) {
			throw new ArduinoUploaderException("Unable to enter programming mode!");
		}
	}

	@Override
	public void LoadAddress(IMemory memory, int offset) {
		if (getLogger() != null)
			getLogger().trace("Sending load address request: %{}.", offset);
		Send(new SetAddressRequest(offset / 2));
		int response = ReceiveNext();
		if (response != Constants.CarriageReturn) {
			throw new ArduinoUploaderException("Unable to execute set address request!");
		}
	}

	@Override
	public byte[] ExecuteReadPage(IMemory memory) {
		MemoryType type = memory.getType();
		int blockSize = memory.getPageSize();
		Send(new StartBlockReadRequest(type, blockSize));
//        StartBlockReadResponse response = this.<StartBlockReadResponse>Receive(blockSize);
		StartBlockReadResponse startBlockReadResponse = new StartBlockReadResponse();
		StartBlockReadResponse response = Receive(startBlockReadResponse, blockSize);

		return response.getBytes();
	}

	@Override
	public void ExecuteWritePage(IMemory memory, int offset, byte[] bytes) {
		MemoryType type = memory.getType();
		int blockSize = memory.getPageSize();
		Send(new StartBlockLoadRequest(type, blockSize, bytes));
		int response = ReceiveNext();
		if (response != Constants.CarriageReturn) {
			throw new ArduinoUploaderException("Unable to execute write page!");
		}
	}

	@Override
	public void LeaveProgrammingMode() {
		Send(new LeaveProgrammingModeRequest());
		int leaveProgModeResp = ReceiveNext();
		if (leaveProgModeResp != Constants.CarriageReturn) {
			throw new ArduinoUploaderException("Unable to leave programming mode!");
		}

		Send(new ExitBootLoaderRequest());
		int exitBootloaderResp = ReceiveNext();
		if (exitBootloaderResp != Constants.CarriageReturn) {
			throw new ArduinoUploaderException("Unable to exit boot loader!");
		}
	}
}