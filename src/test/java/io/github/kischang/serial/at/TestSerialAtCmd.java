package io.github.kischang.serial.at;

import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.StandardCharsets;

/**
 * @author KisChang
 * @date 2021-12-31
 */
public class TestSerialAtCmd {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\nUsing Library Version v" + SerialPort.getVersion());
        SerialPort atPort = SimTechSerialUtils.getAllAtPorts().get(0);
        System.out.println("\nUsing Port: " + (atPort.getSystemPortName() + ": " + atPort.getDescriptivePortName() + " - " + atPort.getPortDescription()));
        if (!atPort.openPort()) {
            System.out.println("open SerialPort error!");
            return;
        }
        atPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        atPort.setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        atPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 1000);

        //关闭命令回显，E0
        outCmd(atPort, "ATE0");
        outCmd(atPort, "AT");

        //测试GPS
        outCmd(atPort, "AT+CGNSPWR=1");
        getGpsInfo(atPort);
        outCmd(atPort, "AT+CGNSPWR=0");


        Thread.sleep(5000);
        atPort.closePort();
    }

    private static void getGpsInfo(SerialPort atPort) {
        // +CGNSINF: 0,,,,,,,,,,,,,,,,,,,,
        // +CGNSINF: 1,1,20211231071437.000,40.182870,116.262341,23.700,0.00,0.0,1,,1.5,1.8,1.0,,7,2,,,32,,
        while (true){
            String rv = outCmd(atPort, "AT+CGNSINF");
            rv = rv.substring(rv.indexOf(":") + 2);
            String[] gps = rv.split(",");
            String utcdate = gps[2];
            String Latitude = gps[3];
            String Longitude = gps[4];
            String haiba = gps[5];
            String sudu = gps[6];
            if (utcdate.length() > 15){
                System.out.println("utcdate  >>" + utcdate);
                System.out.println("Latitude >>" + Latitude);
                System.out.println("Longitude>>" + Longitude);
                System.out.println("haiba    >>" + haiba);
                System.out.println("sudu     >>" + sudu);
                return;
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
        }
    }

    static byte[] readBuffer = new byte[2048];
    private static String outCmd(SerialPort atPort, String at) {
        byte[] write = (at + "\r").getBytes(StandardCharsets.UTF_8);
        atPort.writeBytes(write, write.length);
        System.out.println("write:" + at);
        while (atPort.bytesAvailable() <= 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }
        int readrv = atPort.readBytes(readBuffer, atPort.bytesAvailable());
        String ret = new String(readBuffer, 0, readrv).trim();
        System.out.println("read :" + ret);
        return ret;
    }

}
