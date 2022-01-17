package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Response;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;

public class CheckBlockSupportResponse extends Response
{
	public final boolean getHasBlockSupport()
	{

		return getBytes()[0] == (byte) 'Y';
	}

	public final int getBufferSize()
	{
		return (getBytes()[1] << 8) + getBytes()[2];
	}
}