package com.siliconandsynapse.aclient.game;

import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.types.SheepsHeadFactory;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerInfo;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerInfoObj;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACard;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACardObj;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACardResponse;
import com.siliconandsynapse.ixcpp.protocol.game.TableChange;
import com.siliconandsynapse.ixcpp.protocol.game.TableChangeObj;
import com.siliconandsynapse.ixcpp.protocol.game.TableChangeObjCard;
import com.siliconandsynapse.ixcpp.protocol.game.TableChangeObjSeat;
import com.siliconandsynapse.ixcpp.protocol.game.TableChangeObjStack;
import com.siliconandsynapse.ixcpp.protocol.game.TurnChange;
import com.siliconandsynapse.ixcpp.protocol.game.TurnChangeObj;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.Message;
import com.siliconandsynapse.net.ixtunnel.ParseError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

        SheepsHeadFactory f = new SheepsHeadFactory();
        List<Card> cards = f.getCards();

        IxAddress game = IxAddress.createRoot("TwoSheep");

        List<PlayerInfoObj> x = Stream.of(
                new PlayerInfoObj(0, "player0"),
                new PlayerInfoObj(1, "player1")).collect(Collectors.toList());
        tunnel.sendDocumentFromServer(game.append(PlayerInfo.KEY), new Message(x));

        tunnel.sendDocumentFromServer(game.append(TurnChange.KEY), new Message(new TurnChangeObj(0)));

        var seats = IntStream.range(0, 2)
                .mapToObj(s -> new TableChangeObjSeat(s,

                        Stream.concat(
                        IntStream.range(0, 6).mapToObj(i ->
                            new TableChangeObjStack(i, "public",
                                Stream.of(new TableChangeObjCard(0, cards.remove(0)),
                                    new TableChangeObjCard(1, cards.remove(0))
                                ).collect(Collectors.toList())
                            )
                        ),
                        IntStream.range(0,4).mapToObj(i ->
                            new TableChangeObjStack(i, "private",
                                    Stream.of(new TableChangeObjCard(0, cards.remove(0)))
                                    .collect(Collectors.toList())
                            )
                        )).collect(Collectors.toList())
                    )
                ).collect(Collectors.toList());



        var table = new TableChangeObj(seats);

        tunnel.registerReceiver(new IxReciever() {

            @Override
            public void accept(IxAddress key, IxManager returnTunnel, Message doc) {
                var resp = (PlayerPickACardResponse)doc.getDoc();
                var i = resp.getCode();
            }

            @Override
            public String placeInThread() {
                return null;
            }

            @Override
            public AcceptedAddresses getEvents() {
                return new AcceptedAddresses(game.append(PlayerPickACard.KEY));
            }
        });

        tunnel.sendDocumentFromServer(game.append(TableChange.KEY), new Message(table));
        tunnel.sendDocumentFromServer(game.append(PlayerPickACard.KEY), new Message(new PlayerPickACardObj()));

    }

}
