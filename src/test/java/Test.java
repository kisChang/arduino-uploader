import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;

import io.github.kischang.arduino.uploader.ArduinoUploader.ArduinoSketchUploader;
import io.github.kischang.arduino.uploader.ArduinoUploader.ArduinoSketchUploaderOptions;
import io.github.kischang.arduino.uploader.ArduinoUploader.ArduinoUploaderException;
import io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.ArduinoModel;
import io.github.kischang.arduino.uploader.CSharpStyle.IProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.kischang.arduino.uploader.UsbSerialHelper.SerialPortStreamImpl;

public class Test {

	private static final Logger logger = LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) {

		ArduinoSketchUploaderOptions optionsUno = new ArduinoSketchUploaderOptions();
		optionsUno.setArduinoModel(ArduinoModel.UnoR3);
		optionsUno.setPortName("COM7");// Real comport name or null for first auto
		optionsUno.setFileName("pathto\\firmware.hex");

		IProgress<Double> progress = new IProgress<Double>() {
			@Override
			public void Report(Double value) {
				System.out.println(String.format("Upload progress: %1$,3.2f%%", value * 100));
			}
		};
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
