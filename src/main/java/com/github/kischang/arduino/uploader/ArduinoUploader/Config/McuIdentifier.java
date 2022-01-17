package com.github.kischang.arduino.uploader.ArduinoUploader.Config;

public enum McuIdentifier {
    AtMega1284,
    AtMega2560,
    AtMega32U4,
    AtMega168,
    AtMega328P;

    public static final int SIZE = Integer.SIZE;

    public int getValue() {
        return this.ordinal();
    }

    public static McuIdentifier forValue(int value) {
        return values()[value];
    }
}