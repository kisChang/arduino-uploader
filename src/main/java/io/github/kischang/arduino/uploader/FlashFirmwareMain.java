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

import java.util.ArrayList;
import java.util.List;

/**
 * @author KisChang
 * @date 2022-01-06
 */
public class FlashFirmwareMain {

    private static final Logger logger = LoggerFactory.getLogger(FlashFirmwareMain.class);

    public static void main(String[] args) {
        ArduinoModel model = ArduinoModel.valueOf(args[0]);
        String portName = args[1];
        String firmwareFile = args[2];
        uploader(model, portName, firmwareFile);
    }

    public static void uploader(ArduinoModel model, String portName, String firmwareFile) {
        ArduinoSketchUploaderOptions optionsUno = new ArduinoSketchUploaderOptions();
        optionsUno.setArduinoModel(model);
        optionsUno.setPortName(portName);// Real comport name or null for first auto
        optionsUno.setFileName(firmwareFile);

        IProgress<Double> progress =
                value -> System.out.printf("Upload progress: %1$,3.2f%%%n", value * 100);
        ArduinoSketchUploader<SerialPortStreamImpl> uploader = new ArduinoSketchUploader<SerialPortStreamImpl>(
                SerialPortStreamImpl.class, optionsUno, logger, progress);
        try {
            List<String> portNames = new ArrayList<>();
            SerialPort[] serialPorts = SerialPort.getCommPorts();
            for (SerialPort port : serialPorts) {
                portNames.add(port.getSystemPortName());
                System.out.println(port.getSystemPortName());
            }
            uploader.UploadSketch(portNames.toArray(new String[portNames.size()]));
        } catch (ArduinoUploaderException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Unexpected exception!");
            ex.printStackTrace();
            ex.printStackTrace();
        }
    }
}
