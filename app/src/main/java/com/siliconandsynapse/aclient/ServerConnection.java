package com.siliconandsynapse.aclient;

import androidx.annotation.NonNull;

public record ServerConnection(String name, String address) {

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
