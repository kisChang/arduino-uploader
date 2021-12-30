package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.*;

public class LeaveProgrammingModeResponse extends Response
{
	public final boolean getSuccess()
	{
		return getBytes().length == 2 && getBytes()[0] == Constants.CmdLeaveProgmodeIsp && getBytes()[1] == Constants.StatusCmdOk;
	}
}