package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class SetAddressRequest extends Request
{
	public SetAddressRequest(int offset)
	{

		setBytes(new byte[] {Constants.CmdSetAddress, (byte)((offset >> 8) & 0xff), (byte)(offset & 0xff)});
	}
}