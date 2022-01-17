package com.github.kischang.arduino.uploader.ArduinoUploader.BootloaderProgrammers;


import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.github.kischang.arduino.uploader.ArduinoUploader.ArduinoSketchUploader;
import com.github.kischang.arduino.uploader.CSharpStyle.Func0;
import com.github.kischang.arduino.uploader.CSharpStyle.Func3;
import io.github.kischang.arduino.uploader.ArduinoUploader.*;
import org.slf4j.Logger;

public final class WaitHelper {
    private static final String TAG = WaitHelper.class.getSimpleName();

    private static Logger getLogger() {
        return ArduinoSketchUploader.getLogger();
    }

    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile static Thread workingThread;

    public static <T> T WaitFor(int timeout, int interval, Func0<T> toConsider, Func3<Integer, T, Integer, String> format) {
        Future<T> task = executor.submit(() -> {
            workingThread = Thread.currentThread();
            int i = 0;
            while (!workingThread.isInterrupted()) {
                T item = toConsider.invoke();
                if (getLogger() != null) getLogger().info(format.invoke(i, item, interval));
                if (item != null) return item;
                i++;
                Thread.sleep(interval);
            }
            return null;
        });
        try {
            return task.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            workingThread.interrupt();
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            e.printStackTrace();
        }
        boolean canceled = task.cancel(true);
        executor.shutdown();
        return null;
    }


}