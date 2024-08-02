package com.siliconandsynapse.aclient.Servers;

public class DynamicServerConnection extends ServerConnection {

    private String address;
    private int ticks;
    public DynamicServerConnection(String name, String address, int ticks) {
        super(name, address);
        this.ticks = ticks;
    }

    public boolean expired() {
        if (ticks >= 2)
            return true;
        return false;
    }

    public void increment() {
        if (ticks == -1)
            return;
        ticks++;
    }
    public void reset() {
        if (ticks == -1)
            return;
        ticks = 0;
    }


}
