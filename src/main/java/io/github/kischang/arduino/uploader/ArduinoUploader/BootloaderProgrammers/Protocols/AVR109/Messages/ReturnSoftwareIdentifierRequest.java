package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class ReturnSoftwareIdentifierRequest extends Request
{
	public ReturnSoftwareIdentifierRequest()
	{

		setBytes(new byte[] {Constants.CmdReturnSoftwareIdentifier});
	}
}