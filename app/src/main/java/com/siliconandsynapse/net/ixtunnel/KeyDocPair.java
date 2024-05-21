package com.siliconandsynapse.net.ixtunnel;

public class KeyDocPair
{
    IxAddress key;
    Message doc;
    public KeyDocPair(IxAddress key, Message doc)
    {
        this.key = key;
        this.doc = doc;
    }
    public Message getDoc()
    {
        return doc;
    }
    public IxAddress getKey()
    {
        return key;
    }
}
