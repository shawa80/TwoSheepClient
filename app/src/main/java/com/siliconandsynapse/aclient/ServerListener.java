package com.siliconandsynapse.aclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ServerListener {

    private Thread run;
    private Thread remove;
    private volatile boolean keepRunning;
    private DatagramSocket socket;

    public interface ServerListenerEvent {
        public void serverFound(String name, String address);
    }
    public interface ServerTickEvent {
        public void tick();
    }

    public void stop() {
        keepRunning = false;
        if (socket != null)
            socket.close();
        run.interrupt();
        remove.interrupt();
    }
    public void listen(ServerListenerEvent listener, ServerTickEvent tick) {
        keepRunning = true;

        remove = new Thread(() -> {
            while (keepRunning) {
                try {
                    Thread.sleep(12000);
                    tick.tick();
                } catch (InterruptedException e) {
                    break;
                }
            }

        });
        remove.start();

        try {
            socket = new DatagramSocket(1077);
        } catch (SocketException e) {
            return;
        }

        run = new Thread(() -> {
            try {
                socket.setBroadcast(true);

                byte[] recvBuf = new byte[1000];
                var packet = new DatagramPacket(recvBuf, recvBuf.length);

                while (keepRunning) {
                    socket.receive(packet);
                    byte[] data = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
                    var name = new String(data, StandardCharsets.UTF_8);

                    listener.serverFound(name, packet.getAddress().getHostAddress());
                }
            } catch (IOException e) {

            } finally {
                socket.close();

            }

        });
        run.start();
    }

}
