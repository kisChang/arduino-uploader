package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class SelectDeviceTypeRequest extends Request
{

	public SelectDeviceTypeRequest(byte deviceCode)
	{

		setBytes(new byte[] {Constants.CmdSelectDeviceType, deviceCode});
	}
}