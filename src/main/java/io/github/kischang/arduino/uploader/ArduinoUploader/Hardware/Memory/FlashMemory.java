package io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory;

public class FlashMemory extends Memory {
    @Override
    public MemoryType getType() {
        return MemoryType.Flash;
    }
}