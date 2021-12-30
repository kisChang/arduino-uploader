package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;

public class ReturnSoftwareVersionResponse extends Response
{
	public final char getMajorVersion()
	{
		return (char) getBytes()[0];
	}

	public final char getMinorVersion()
	{
		return (char) getBytes()[1];
	}
}