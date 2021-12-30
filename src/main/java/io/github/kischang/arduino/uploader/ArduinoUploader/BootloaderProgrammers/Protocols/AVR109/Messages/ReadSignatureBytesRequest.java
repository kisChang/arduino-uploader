package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class ReadSignatureBytesRequest extends Request
{
	public ReadSignatureBytesRequest()
	{

		setBytes(new byte[] {Constants.CmdReadSignatureBytes});
	}
}