package com.siliconandsynapse.aclient;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ServerConnection {

    private String name;
    private String address;
    private int ticks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConnection that = (ServerConnection) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    public ServerConnection(String name, String address, int ticks) {
        this.name = name;
        this.address = address;
        this.ticks = ticks;
    }

    public String name() {
        return name;
    }
    public String address() {
        return address;
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



    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
