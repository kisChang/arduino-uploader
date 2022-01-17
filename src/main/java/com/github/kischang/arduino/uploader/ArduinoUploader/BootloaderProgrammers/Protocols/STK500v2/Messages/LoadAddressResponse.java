package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Response;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Constants;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.*;

public class LoadAddressResponse extends Response
{
	public final boolean getSucceeded()
	{
		return getBytes().length == 2 && getBytes()[0] == Constants.CmdLoadAddress && getBytes()[1] == Constants.StatusCmdOk;
	}
}