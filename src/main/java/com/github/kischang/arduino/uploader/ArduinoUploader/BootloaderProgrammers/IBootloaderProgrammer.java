package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers;

import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.IMemory;
import com.github.kischang.arduino.uploader.CSharpStyle.IProgress;
import com.github.kischang.arduino.uploader.UsbSerialHelper.ISerialPortStream;
import io.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.*;
import IntelHexFormatReader.Model.*;

public interface IBootloaderProgrammer<E extends ISerialPortStream> {
    void Open() throws ClassNotFoundException;

    void Close() throws InterruptedException, ClassNotFoundException;

    void EstablishSync();

    void CheckDeviceSignature();

    void InitializeDevice();

    void EnableProgrammingMode();

    void LeaveProgrammingMode();

    void ProgramDevice(MemoryBlock memoryBlock);

    //C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: void ProgramDevice(MemoryBlock memoryBlock, IProgress<double> progress = null);
    void ProgramDevice(MemoryBlock memoryBlock, IProgress<Double> progress);//IProgress nơ is callback

    void LoadAddress(IMemory memory, int offset);

    void ExecuteWritePage(IMemory memory, int offset, byte[] bytes);

    byte[] ExecuteReadPage(IMemory memory);
}