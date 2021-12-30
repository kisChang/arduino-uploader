package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.*;

public class GetSyncResponse extends Response
{
	public final boolean getIsInSync()
	{
		return getBytes().length > 0 && getBytes()[0] == Constants.RespStkInsync;
	}
}