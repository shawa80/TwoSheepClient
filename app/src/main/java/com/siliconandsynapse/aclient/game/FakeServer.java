package com.siliconandsynapse.aclient.game;

import com.siliconandsynapse.ixcpp.protocol.game.PlayerInfoObj;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.Message;
import com.siliconandsynapse.net.ixtunnel.ParseError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FakeServer {

    private IxManager tunnel;

    public FakeServer()  {
        tunnel = new IxManager();
    }

    public IxManager getTunnel(){
        return tunnel;
    }

    public void start() {

        IxAddress game = IxAddress.createRoot("TwoSheep");

        List<PlayerInfoObj> x = Stream.of(
                new PlayerInfoObj(0, "player0"),
                new PlayerInfoObj(1, "player1")).collect(Collectors.toList());
        tunnel.sendDocumentFromServer(game.append("PlayerInfo"), new Message(x));


    }

}
