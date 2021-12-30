package io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.ResetBehavior;

import io.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers.*;
import io.github.kischang.arduino.uploader.UsbSerialHelper.ISerialPortStream;

public interface IResetBehavior
{
	ISerialPortStream Reset(ISerialPortStream serialPort, SerialPortConfig config);
}