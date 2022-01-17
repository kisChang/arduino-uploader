package com.github.kischang.arduino.uploader.ArduinoUploader;

import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.ArduinoModel;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.*;

public class ArduinoSketchUploaderOptions {
    private String FileName = null;

    public final String getFileName() {
        return FileName;
    }

    public final void setFileName(String value) {
        FileName = value;
    }

    private Iterable<String> hexFileContents;

    public Iterable<String> getHexFileContents() {
        return hexFileContents;
    }

    public void setHexFileContents(Iterable<String> hexFileContents) {
        this.hexFileContents = hexFileContents;
    }

    private String PortName;

    public final String getPortName() {
        return PortName;
    }

    public final void setPortName(String value) {
        PortName = value;
    }

    private com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.ArduinoModel ArduinoModel = getArduinoModel().values()[0];

    public final ArduinoModel getArduinoModel() {
        return ArduinoModel;
    }

    public final void setArduinoModel(ArduinoModel value) {
        ArduinoModel = value;
    }
}