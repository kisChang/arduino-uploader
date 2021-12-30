package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;

public class ExecuteProgramPageResponse extends Response {

    public final byte getAnswerId() {
        return getBytes()[0];
    }

    public final byte getStatus() {
        return getBytes()[1];
    }
}