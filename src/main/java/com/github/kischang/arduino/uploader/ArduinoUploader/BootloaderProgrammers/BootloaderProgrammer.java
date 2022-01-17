package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers;

import java.util.Arrays;

import com.github.kischang.arduino.uploader.ArduinoUploader.ArduinoSketchUploader;
import com.github.kischang.arduino.uploader.ArduinoUploader.ArduinoUploaderException;
import com.github.kischang.arduino.uploader.CSharpStyle.BitConverter;
import com.github.kischang.arduino.uploader.CSharpStyle.IProgress;
import com.github.kischang.arduino.uploader.UsbSerialHelper.ISerialPortStream;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.IMcu;
import com.github.kischang.arduino.uploader.ArduinoUploader.Hardware.Memory.IMemory;
import IntelHexFormatReader.Model.MemoryBlock;
import IntelHexFormatReader.Model.MemoryCell;
import org.slf4j.Logger;

public abstract class BootloaderProgrammer<E extends ISerialPortStream> implements IBootloaderProgrammer<E> {
	protected final Logger getLogger() {
		return ArduinoSketchUploader.getLogger();
	}

	private IMcu Mcu;

	protected final IMcu getMcu() {
		return Mcu;
	}

	protected BootloaderProgrammer(IMcu mcu) {
		Mcu = mcu;
	}

	public abstract void Open();

	public abstract void Close() throws InterruptedException;

	public abstract void EstablishSync();

	public abstract void CheckDeviceSignature();

	public abstract void InitializeDevice();

	public abstract void EnableProgrammingMode();

	public abstract void LeaveProgrammingMode();

	public abstract void LoadAddress(IMemory memory, int offset);

	public abstract void ExecuteWritePage(IMemory memory, int offset, byte[] bytes);

	public abstract byte[] ExecuteReadPage(IMemory memory);

	public void ProgramDevice(MemoryBlock memoryBlock) {
		ProgramDevice(memoryBlock, null);
	}

	public void ProgramDevice(MemoryBlock memoryBlock, IProgress<Double> progress) {
		int sizeToWrite = memoryBlock.getHighestModifiedOffset() + 1;
		IMemory flashMem = getMcu().getFlash();
		int pageSize = flashMem.getPageSize();
		if (getLogger() != null)
			getLogger().info("Preparing to write ${} bytes...", sizeToWrite);
		if (getLogger() != null)
			getLogger().info("Flash page size: ${}.", pageSize);

		int offset;
		for (offset = 0; offset < sizeToWrite; offset += pageSize) {
			if (getLogger() != null)
				progress.Report((double) offset / (sizeToWrite * 2));

			boolean needsWrite = false;
			for (int i = offset; i < offset + pageSize; i++) {
				if (!memoryBlock.getCells()[i].getModified()) {
					continue;
				}
				needsWrite = true;
				break;
			}
			if (needsWrite) {
//              var bytesToCopy = memoryBlock.Cells.Skip(offset).Take(pageSize).Select(x => x.Value).ToArray();
				MemoryCell[] orgMemoryCells = memoryBlock.getCells();
				MemoryCell[] memoryCells;
				if ((offset + pageSize) > orgMemoryCells.length)
					memoryCells = Arrays.copyOfRange(orgMemoryCells, offset, orgMemoryCells.length);
				else
					memoryCells = Arrays.copyOfRange(orgMemoryCells, offset, offset + pageSize);
				byte[] bytesToCopy = new byte[memoryCells.length];
				for (int i = 0; i < memoryCells.length; i++) {
					bytesToCopy[i] = memoryCells[i].getValue();
				}
				if (getLogger() != null)
					getLogger().trace("Writing page at offset ${}.", offset);
				LoadAddress(flashMem, offset);
				ExecuteWritePage(flashMem, offset, bytesToCopy);
			} else {
				if (getLogger() != null)
					getLogger().trace("Skip writing page...");
			}
		}
		if (getLogger() != null)
			getLogger().info("${} bytes written to flash memory!", sizeToWrite);
	}

	public void VerifyProgram(MemoryBlock memoryBlock) {
		VerifyProgram(memoryBlock, null);
	}

	// public virtual void VerifyProgram(MemoryBlock memoryBlock, IProgress<double>
	// progress = null)
	public void VerifyProgram(MemoryBlock memoryBlock, IProgress<Double> progress) {
		int sizeToVerify = memoryBlock.getHighestModifiedOffset() + 1;
		IMemory flashMem = getMcu().getFlash();
		int pageSize = flashMem.getPageSize();
		if (getLogger() != null)
			getLogger().info("Preparing to verify ${} bytes...", sizeToVerify);
		if (getLogger() != null)
			getLogger().info("Flash page size: ${}.", pageSize);
		int offset;
		for (offset = 0; offset < sizeToVerify; offset += pageSize) {
			if (progress != null)
				progress.Report((double) (sizeToVerify + offset) / (sizeToVerify * 2));
			if (getLogger() != null)
				getLogger().debug("Executing verification of bytes @ address ${} (page size ${})...",
						offset, pageSize);

//          var bytesToVerify = memoryBlock.Cells.Skip(offset).Take(pageSize).Select(x => x.Value).ToArray();
			MemoryCell[] orgMemoryCells = memoryBlock.getCells();
			MemoryCell[] memoryCells;
			if ((offset + pageSize) > orgMemoryCells.length)
				memoryCells = Arrays.copyOfRange(orgMemoryCells, offset, orgMemoryCells.length);
			else
				memoryCells = Arrays.copyOfRange(orgMemoryCells, offset, offset + pageSize);
			byte[] bytesToVerify = new byte[memoryCells.length];
			for (int i = 0; i < memoryCells.length; i++) {
				bytesToVerify[i] = memoryCells[i].getValue();
			}
			LoadAddress(flashMem, offset);
			// var bytesPresent = ExecuteReadPage(flashMem);
			byte[] bytesPresent = ExecuteReadPage(flashMem);
			// var succeeded = bytesToVerify.SequenceEqual(bytesPresent);
			boolean succeeded = Arrays.equals(bytesToVerify, bytesPresent);
			if (succeeded) {
				continue;
			}
			if (getLogger() != null)
				getLogger().info("Expected: ${}. ${} Read after write: ${}", BitConverter.toString(bytesToVerify), System.getProperty("line.separator"),
								BitConverter.toString(bytesPresent));
			throw new ArduinoUploaderException("Difference encountered during verification!");
		}
		if (getLogger() != null)
			getLogger().info("${} bytes verified!", sizeToVerify);
	}

}