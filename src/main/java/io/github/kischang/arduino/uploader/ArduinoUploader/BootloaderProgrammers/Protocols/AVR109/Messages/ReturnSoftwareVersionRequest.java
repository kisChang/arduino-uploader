package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class ReturnSoftwareVersionRequest extends Request
{
	public ReturnSoftwareVersionRequest()
	{

		setBytes(new byte[] {Constants.CmdReturnSoftwareVersion});
	}
}