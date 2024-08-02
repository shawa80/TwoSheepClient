package com.siliconandsynapse.aclient.Servers;

import androidx.annotation.NonNull;

import java.util.Objects;

public abstract class ServerConnection {

    private String name;
    private String address;

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

    public ServerConnection(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String name() {
        return name;
    }
    public String address() {
        return address;
    }



    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
