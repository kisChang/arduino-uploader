package com.github.kischang.arduino.uploader;

import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.ArduinoModel;
import org.junit.Test;

import java.io.IOException;

public class TestUploader {

	@Test
	public void testNano() throws IOException {
		FlashFirmwareMain.uploader(ArduinoModel.NanoBLE
				, "COM24"
				, "E:\\IOTdev\\testnano\\.pio\\build\\nanoatmega328new\\firmware.hex");
	}

	@Test
	public void testUno() throws IOException {
		FlashFirmwareMain.uploader(ArduinoModel.UnoR3
				, "COM7"
				, "C:\\Users\\KisChang\\Desktop\\grbl_v1.1h.20190825.hex");
	}

}
