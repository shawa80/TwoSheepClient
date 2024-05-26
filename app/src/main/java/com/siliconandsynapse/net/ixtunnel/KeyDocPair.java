package com.siliconandsynapse.net.ixtunnel;

public class KeyDocPair
{
    IxAddress key;
    String doc;
    public KeyDocPair(IxAddress key, String doc)
    {
        this.key = key;
        this.doc = doc;
    }
    public String getDoc()
    {
        return doc;
    }
    public IxAddress getKey()
    {
        return key;
    }
}
