package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Request;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.Constants;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v1.*;

public class GetParameterRequest extends Request {

    public GetParameterRequest(byte param) {

        setBytes(new byte[]{Constants.CmdStkGetParameter, param, Constants.SyncCrcEop});
    }
}