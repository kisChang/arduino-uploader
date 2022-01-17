package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Constants;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Request;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.MemoryType;
import io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class StartBlockReadRequest extends Request
{
	public StartBlockReadRequest(MemoryType memType, int blockSize)
	{
		setBytes(new byte[] {Constants.CmdStartBlockRead, (byte)(blockSize >> 8), (byte)(blockSize & 0xff), (byte)(memType == MemoryType.Flash ? 'F' : 'E')});
	}
}