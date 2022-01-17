package com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory;

public class EepromMemory extends Memory {
    @Override
    public MemoryType getType() {
        return MemoryType.Eeprom;
    }
}