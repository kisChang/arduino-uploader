package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.*;

public class GetSyncRequest extends Request
{
	public GetSyncRequest()
	{
		setBytes(new byte[] {Constants.CmdStkGetSync, Constants.SyncCrcEop});
	}
}