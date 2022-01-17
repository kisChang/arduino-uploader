package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Constants;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Request;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class EnterProgrammingModeRequest extends Request
{
	public EnterProgrammingModeRequest()
	{

		setBytes(new byte[] {Constants.CmdEnterProgrammingMode});
	}
}