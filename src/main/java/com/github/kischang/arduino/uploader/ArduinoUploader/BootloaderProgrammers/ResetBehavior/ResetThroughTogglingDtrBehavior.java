package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.ResetBehavior;



import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.SerialPortConfig;
import com.github.kischang.arduino.uploader.UsbSerialHelper.ISerialPortStream;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.*;

public class ResetThroughTogglingDtrBehavior implements IResetBehavior {
	private boolean Toggle;

	private boolean getToggle() {
		return Toggle;
	}

	public ResetThroughTogglingDtrBehavior(boolean toggle) {
		Toggle = toggle;
	}

	@Override
	public final ISerialPortStream Reset(ISerialPortStream serialPort, SerialPortConfig config) {
		serialPort.setDtrEnable(getToggle());
		return serialPort;
	}
}