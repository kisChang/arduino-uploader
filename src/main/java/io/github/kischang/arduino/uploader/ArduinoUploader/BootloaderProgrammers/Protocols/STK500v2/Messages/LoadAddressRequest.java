package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.*;

public class LoadAddressRequest extends Request {
    public LoadAddressRequest(IMemory memory, int addr) {
        int modifier = memory.getType() == MemoryType.Flash ? 0x80 : 0x00;

        setBytes(new byte[]{Constants.CmdLoadAddress, (byte) (((addr >> 24) & 0xff) | modifier), (byte) ((addr >> 16) & 0xff), (byte) ((addr >> 8) & 0xff), (byte) (addr & 0xff)});
    }
}