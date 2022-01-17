package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Request;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Constants;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.*;

public class EnableProgrammingModeRequest extends Request
{
	public EnableProgrammingModeRequest()
	{
		setBytes(new byte[] {Constants.CmdStkEnterProgmode, Constants.SyncCrcEop});
	}
}