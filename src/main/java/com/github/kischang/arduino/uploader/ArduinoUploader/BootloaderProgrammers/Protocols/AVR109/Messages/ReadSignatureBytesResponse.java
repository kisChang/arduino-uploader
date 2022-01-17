package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Response;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;

public class ReadSignatureBytesResponse extends Response
{

	public final byte[] getSignature()
	{

		return new byte[] {getBytes()[2], getBytes()[1], getBytes()[0]};
	}
}