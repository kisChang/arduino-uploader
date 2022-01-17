package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.Protocols;

public interface IMessage {
    byte[] getBytes();

    void setBytes(byte[] value);
}