package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Request;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.IMemory;
import io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;

public class ExecuteReadPageRequest extends Request {

    public ExecuteReadPageRequest(byte readCmd, IMemory memory) {
        int pageSize = memory.getPageSize();
        byte cmdByte = memory.getCmdBytesRead()[0];
        setBytes(new byte[]{readCmd, (byte) (pageSize >> 8), (byte) (pageSize & 0xff), cmdByte});
    }
}