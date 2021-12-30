package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.*;

public class LeaveProgrammingModeRequest extends Request
{
	public LeaveProgrammingModeRequest()
	{

		setBytes(new byte[] {Constants.CmdLeaveProgmodeIsp, (byte) 0x01, (byte) 0x01});
	}
}