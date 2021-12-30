package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class CheckBlockSupportRequest extends Request
{
	public CheckBlockSupportRequest()
	{

		setBytes(new byte[] {Constants.CmdCheckBlockSupport});
	}
}