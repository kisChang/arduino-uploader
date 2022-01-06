package io.github.kischang.serial.at;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author KisChang
 * @date 2021-12-31
 */
public class SimTechSerialUtils {

    public static List<SerialPort> getAllAtPorts(){
        return filterPorts("SimTech HS-USB AT Port");
    }

    public static List<SerialPort> getAllGPSPorts(){
        return filterPorts("SimTech HS-USB NMEA");
    }

    public static List<SerialPort> filterPorts(String filter){
        return Arrays.stream(SerialPort.getCommPorts()).filter(
                serialPort -> serialPort.getDescriptivePortName().startsWith(filter)
        ).collect(Collectors.toList());
    }

}
