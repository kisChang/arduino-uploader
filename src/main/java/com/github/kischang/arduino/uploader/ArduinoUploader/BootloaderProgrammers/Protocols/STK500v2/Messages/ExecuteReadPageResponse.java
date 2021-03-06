package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Response;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;

public class ExecuteReadPageResponse extends Response {

    public final byte getAnswerId() {
        return getBytes()[0];
    }

    public final byte getStatus() {
        return getBytes()[1];
    }
}