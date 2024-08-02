package com.siliconandsynapse.aclient.Servers;

import android.widget.EditText;

public class ConfigurableServerConnection  extends ServerConnection {

    private EditText textView;

    public ConfigurableServerConnection(String name, EditText textView) {
        super(name, "");

        this.textView = textView;
    }

    @Override
    public String address() {

        return textView.getText().toString();
    }


}
