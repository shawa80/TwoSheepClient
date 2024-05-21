package com.siliconandsynapse.net.ixtunnel;

public class Message {
    private final Object doc;

    public Message(Object doc)
    {
        this.doc = doc;
    }

    public Object getDoc() { return doc; }

}
