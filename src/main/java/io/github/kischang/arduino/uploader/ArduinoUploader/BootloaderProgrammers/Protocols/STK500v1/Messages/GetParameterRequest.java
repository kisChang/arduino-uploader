package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.*;

public class GetParameterRequest extends Request {

    public GetParameterRequest(byte param) {

        setBytes(new byte[]{Constants.CmdStkGetParameter, param, Constants.SyncCrcEop});
    }
}