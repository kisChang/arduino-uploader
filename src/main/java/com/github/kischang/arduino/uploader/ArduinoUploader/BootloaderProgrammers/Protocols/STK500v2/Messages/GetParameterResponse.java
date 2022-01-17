package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Response;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Constants;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.*;

public class GetParameterResponse extends Response {
    public final boolean getIsSuccess() {
        return getBytes().length > 2 && getBytes()[0] == Constants.CmdGetParameter && getBytes()[1] == Constants.StatusCmdOk;
    }

    public final byte getParameterValue() {
        return getBytes()[2];
    }
}