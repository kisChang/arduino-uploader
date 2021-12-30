package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class ReturnSupportedDeviceCodesRequest extends Request
{
	public ReturnSupportedDeviceCodesRequest()
	{

		setBytes(new byte[] {Constants.CmdReturnSupportedDeviceCodes});
	}
}