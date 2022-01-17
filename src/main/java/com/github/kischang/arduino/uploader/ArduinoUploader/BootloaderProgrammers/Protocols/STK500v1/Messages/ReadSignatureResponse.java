package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Response;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Constants;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.*;

public class ReadSignatureResponse extends Response
{
	public final boolean getIsCorrectResponse()
	{
		return getBytes().length == 4 && getBytes()[3] == Constants.RespStkOk;
	}

	public final byte[] getSignature()
	{

		return new byte[] {getBytes()[0], getBytes()[1], getBytes()[2]};
	}
}