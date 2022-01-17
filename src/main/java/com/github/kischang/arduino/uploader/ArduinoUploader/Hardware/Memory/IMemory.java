package com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory;

public interface IMemory {
    MemoryType getType();

    int getSize();

    int getPageSize();

    byte getPollVal1();

    byte getPollVal2();

    byte getDelay();

    byte[] getCmdBytesRead();

    byte[] getCmdBytesWrite();
}