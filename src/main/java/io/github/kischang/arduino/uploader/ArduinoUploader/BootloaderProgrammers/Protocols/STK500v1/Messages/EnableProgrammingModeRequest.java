package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.*;

public class EnableProgrammingModeRequest extends Request
{
	public EnableProgrammingModeRequest()
	{
		setBytes(new byte[] {Constants.CmdStkEnterProgmode, Constants.SyncCrcEop});
	}
}