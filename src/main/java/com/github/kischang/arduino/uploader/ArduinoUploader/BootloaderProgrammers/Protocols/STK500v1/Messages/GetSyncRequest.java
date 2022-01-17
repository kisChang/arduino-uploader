package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Request;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Constants;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.*;

public class GetSyncRequest extends Request
{
	public GetSyncRequest()
	{
		setBytes(new byte[] {Constants.CmdStkGetSync, Constants.SyncCrcEop});
	}
}