package com.siliconandsynapse.aclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ServerListener {

    public void listen(LoginActivity act) {

        try {
        //, InetAddress.getByName("0.0.0.0")
            var socket = new DatagramSocket(1077);
            socket.setBroadcast(true);

            byte[] recvBuf = new byte[1000];
            var packet = new DatagramPacket(recvBuf, recvBuf.length);

            while (true) {
                socket.receive(packet);
                byte[] data = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
                var name = new String(data, StandardCharsets.UTF_8);

                act.addServer(name, packet.getAddress().getHostAddress());
            }
        } catch (IOException e) {
            System.out.print("");
        }
    }

}
