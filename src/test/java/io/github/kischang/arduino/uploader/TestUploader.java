package io.github.kischang.arduino.uploader;

import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;

import io.github.kischang.arduino.uploader.ArduinoUploader.ArduinoSketchUploader;
import io.github.kischang.arduino.uploader.ArduinoUploader.ArduinoSketchUploaderOptions;
import io.github.kischang.arduino.uploader.ArduinoUploader.ArduinoUploaderException;
import io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.ArduinoModel;
import io.github.kischang.arduino.uploader.CSharpStyle.IProgress;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.kischang.arduino.uploader.UsbSerialHelper.SerialPortStreamImpl;

public class TestUploader {

	private static final Logger logger = LoggerFactory.getLogger(TestUploader.class);


	@Test
	public void testNano() {
		testUploader(ArduinoModel.NanoBLE
				, "COM24"
				, "E:\\IOTdev\\testnano\\.pio\\build\\nanoatmega328new\\firmware.hex");
	}

	@Test
	public void testUno() {
		testUploader(ArduinoModel.UnoR3
				, "COM7"
				, "");
	}

	public void testUploader(ArduinoModel model, String portName, String firmwareFile) {
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
