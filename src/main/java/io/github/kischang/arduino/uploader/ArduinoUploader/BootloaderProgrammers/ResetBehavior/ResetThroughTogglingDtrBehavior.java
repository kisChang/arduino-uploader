package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.ResetBehavior;



import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.*;
import io.github.kischang.arduino.uploader.UsbSerialHelper.ISerialPortStream;

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