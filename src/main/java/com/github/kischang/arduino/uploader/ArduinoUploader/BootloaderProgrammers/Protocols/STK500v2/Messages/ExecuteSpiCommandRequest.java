package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Messages;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.Request;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.Constants;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.STK500v2.*;

public class ExecuteSpiCommandRequest extends Request {

    public ExecuteSpiCommandRequest(byte numTx, byte numRx, byte rxStartAddr, byte[] txData) {
        byte[] data = new byte[numTx];
        System.arraycopy(txData, 0, data, 0, numTx);
        byte[] header = new byte[]{Constants.CmdSpiMulti, numTx, numRx, rxStartAddr};
        setBytes(Concat(header, data));
    }

    static byte[] Concat(byte[] a, byte[] b) {
        byte[] output = new byte[a.length + b.length];
        for (int i = 0; i < a.length; i++)
            output[i] = a[i];
        for (int j = 0; j < b.length; j++)
            output[a.length + j] = b[j];
        return output;
    }
}