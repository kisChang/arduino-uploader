package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class ReturnProgrammerTypeRequest extends Request
{
	public ReturnProgrammerTypeRequest()
	{

		setBytes(new byte[] {Constants.CmdReturnProgrammerType});
	}
}