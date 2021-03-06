package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Request;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Constants;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.IMemory;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.MemoryType;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.*;

public class LoadAddressRequest extends Request {
    public LoadAddressRequest(IMemory memory, int addr) {
        int modifier = memory.getType() == MemoryType.Flash ? 0x80 : 0x00;

        setBytes(new byte[]{Constants.CmdLoadAddress, (byte) (((addr >> 24) & 0xff) | modifier), (byte) ((addr >> 16) & 0xff), (byte) ((addr >> 8) & 0xff), (byte) (addr & 0xff)});
    }
}