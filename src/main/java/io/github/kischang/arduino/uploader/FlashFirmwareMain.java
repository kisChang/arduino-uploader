package io.github.kischang.arduino.uploader;

import com.fazecast.jSerialComm.SerialPort;
import io.github.kischang.arduino.uploader.ArduinoUploader.ArduinoSketchUploader;
import io.github.kischang.arduino.uploader.ArduinoUploader.ArduinoSketchUploaderOptions;
import io.github.kischang.arduino.uploader.ArduinoUploader.ArduinoUploaderException;
import io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.ArduinoModel;
import io.github.kischang.arduino.uploader.CSharpStyle.IProgress;
import io.github.kischang.arduino.uploader.UsbSerialHelper.SerialPortStreamImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KisChang
 * @date 2022-01-06
 */
public class FlashFirmwareMain {

    private static final Logger logger = LoggerFactory.getLogger(FlashFirmwareMain.class);

    public static void main(String[] args) throws IOException {
        ArduinoModel model = ArduinoModel.valueOf(args[0]);
        String portName = args[1];
        String firmwareFile = args[2];
        uploader(model, portName, firmwareFile);
    }

    private static final IProgress<Double> progressHandlerDef = value -> System.out.printf("Upload progress: %1$,3.2f%%%n", value * 100);

    public static void uploader(ArduinoModel model, String portName, String firmwareFile) throws IOException {
        uploader(model, portName, HexFileUtils.fileToHexContents(firmwareFile), progressHandlerDef);
    }

    public static void uploader(ArduinoModel model, String portName, Iterable<String> hexFileContents, IProgress<Double> progress) {
        ArduinoSketchUploaderOptions optionsUno = new ArduinoSketchUploaderOptions();
        optionsUno.setArduinoModel(model);
        optionsUno.setPortName(portName);// Real comport name or null for first auto
        optionsUno.setHexFileContents(hexFileContents);

        ArduinoSketchUploader<SerialPortStreamImpl> uploader = new ArduinoSketchUploader<SerialPortStreamImpl>(
                SerialPortStreamImpl.class, optionsUno, logger, progress);
        try {
            List<String> portNames = new ArrayList<>();
            SerialPort[] serialPorts = SerialPort.getCommPorts();
            for (SerialPort port : serialPorts) {
                portNames.add(port.getSystemPortName());
                System.out.println(port.getSystemPortName());
            }
            uploader.UploadSketch(portNames.toArray(new String[0]));
        } catch (ArduinoUploaderException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Unexpected exception!");
            ex.printStackTrace();
        }
    }
}
