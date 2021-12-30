package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;

public class ReturnProgrammerTypeResponse extends Response
{
	public final char getProgrammerType()
	{
		return (char) getBytes()[0];
	}
}