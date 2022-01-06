package io.github.kischang.arduino.uploader;

import io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.ArduinoModel;
import org.junit.Test;

public class TestUploader {

	@Test
	public void testNano() {
		FlashFirmwareMain.uploader(ArduinoModel.NanoBLE
				, "COM24"
				, "E:\\IOTdev\\testnano\\.pio\\build\\nanoatmega328new\\firmware.hex");
	}

	@Test
	public void testUno() {
		FlashFirmwareMain.uploader(ArduinoModel.UnoR3
				, "COM7"
				, "");
	}

}
