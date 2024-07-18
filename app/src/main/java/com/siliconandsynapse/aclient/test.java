package com.siliconandsynapse.aclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class test {

    private DatagramSocket socket;

    public test() {


    }

    public void start(String serverName) {
        new Thread(() -> {
            try {
                socket = new DatagramSocket(1078);
                socket.setBroadcast(true);

                while (true) {

                    var buf = serverName.getBytes(StandardCharsets.UTF_8);
                    var group = InetAddress.getByName("255.255.255.255");
                    var packet = new DatagramPacket(buf, buf.length, group, 1077);

                    socket.send(packet);

                    Thread.sleep(5000);
                }
                //socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        })
                .start();
    }

}
