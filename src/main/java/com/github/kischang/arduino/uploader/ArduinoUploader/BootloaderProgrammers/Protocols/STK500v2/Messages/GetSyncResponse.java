package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import java.io.UnsupportedEncodingException;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Response;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Constants;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.*;

public class GetSyncResponse extends Response {
    public final boolean getIsInSync() {
        return getBytes().length > 1 && getBytes()[0] == Constants.CmdSignOn && getBytes()[1] == Constants.StatusCmdOk;
    }

    public final String getSignature() {
        byte signatureLength = getBytes()[2];
        byte[] signature = new byte[signatureLength];
        System.arraycopy(getBytes(), 3, signature, 0, signatureLength);

        try {
            return new String(signature, 0, signature.length, "ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}