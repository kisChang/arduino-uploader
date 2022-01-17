package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Request;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Constants;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.MemoryType;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.*;

public class ExecuteReadPageRequest extends Request {
    public ExecuteReadPageRequest(MemoryType memType, int pageSize) {

        setBytes(new byte[5]);
        getBytes()[0] = Constants.CmdStkReadPage;
        getBytes()[1] = (byte) ((pageSize >> 8) & 0xff);
        getBytes()[2] = (byte) (pageSize & 0xff);
        getBytes()[3] = (byte) (memType == MemoryType.Eeprom ? 'E' : 'F');
        getBytes()[4] = Constants.SyncCrcEop;
    }
}