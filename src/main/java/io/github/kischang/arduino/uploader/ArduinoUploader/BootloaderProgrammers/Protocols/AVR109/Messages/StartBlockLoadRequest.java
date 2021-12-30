package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.Messages;

import io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.*;
import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols.AVR109.*;

public class StartBlockLoadRequest extends Request {

    public StartBlockLoadRequest(MemoryType memType, int blockSize, byte[] bytes) {

        setBytes(new byte[blockSize + 4]);
        getBytes()[0] = Constants.CmdStartBlockLoad;

        getBytes()[1] = (byte) (blockSize >> 8);

        getBytes()[2] = (byte) (blockSize & 0xff);

        getBytes()[3] = (byte) (memType == MemoryType.Flash ? 'F' : 'E');
        System.arraycopy(bytes, 0, getBytes(), 4, blockSize);
    }
}