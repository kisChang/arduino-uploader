package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.ResetBehavior;

import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.SerialPortConfig;
import com.github.kischang.arduino.uploader.UsbSerialHelper.ISerialPortStream;
import com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.*;

public interface IResetBehavior
{
	ISerialPortStream Reset(ISerialPortStream serialPort, SerialPortConfig config);
}