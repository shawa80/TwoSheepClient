//package com.siliconandsynapse.aclient.game;
//
//import com.google.gson.Gson;
//import com.siliconandsynapse.ixcpp.common.cards.Card;
//import com.siliconandsynapse.ixcpp.common.cards.types.SheepsHeadFactory;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerInfo;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerInfoObj;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACard;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACardObj;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACardResponse;
//import com.siliconandsynapse.ixcpp.protocol.game.TableChange;
//import com.siliconandsynapse.ixcpp.protocol.game.TableChangeObj;
//import com.siliconandsynapse.ixcpp.protocol.game.TableChangeObjCard;
//import com.siliconandsynapse.ixcpp.protocol.game.TableChangeObjSeat;
//import com.siliconandsynapse.ixcpp.protocol.game.TableChangeObjStack;
//import com.siliconandsynapse.ixcpp.protocol.game.TurnChange;
//import com.siliconandsynapse.ixcpp.protocol.game.TurnChangeObj;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import java.util.stream.Stream;
//
//public class FakeServer {
//
//    private IxManager tunnel;
//
//    public FakeServer()  {
//        //tunnel = new IxManager();
//    }
//
//    public IxManager getTunnel(){
//        return tunnel;
//    }
//
//    public void start() {
//
//        String msg = "";
//        var gson = new Gson();
//
//
//        SheepsHeadFactory f = new SheepsHeadFactory();
//        List<Card> cards = f.getCards();
//
//        IxAddress game = IxAddress.createRoot("TwoSheep");
//
//        List<PlayerInfoObj> x = Stream.of(
//                new PlayerInfoObj("player0", 0),
//                new PlayerInfoObj("player1", 1)).collect(Collectors.toList());
//        msg = gson.toJson(x);
//        tunnel.sendDocumentFromServer(game.append(PlayerInfo.KEY), msg);
//
//        msg = gson.toJson((new TurnChangeObj(0)));
//        tunnel.sendDocumentFromServer(game.append(TurnChange.KEY), msg);
//
//        var seats = IntStream.range(0, 2)
//                .mapToObj(s -> new TableChangeObjSeat(s,
//
//                        Stream.concat(
//                        IntStream.range(0, 6).mapToObj(i ->
//                            new TableChangeObjStack(i, "public",
//                                Stream.of(new TableChangeObjCard(0, cards.remove(0)),
//                                    new TableChangeObjCard(1, cards.remove(0))
//                                ).collect(Collectors.toList())
//                            )
//                        ),
//                        IntStream.range(0,4).mapToObj(i ->
//                            new TableChangeObjStack(i, "private",
//                                    Stream.of(new TableChangeObjCard(0, cards.remove(0)))
//                                    .collect(Collectors.toList())
//                            )
//                        )).collect(Collectors.toList())
//                    )
//                ).collect(Collectors.toList());
//
//
//
//        var table = new TableChangeObj(seats);
//
//        tunnel.registerReceiver(new IxReciever() {
//
//            @Override
//            public void accept(IxAddress key, IxManager returnTunnel, String doc) {
//                var resp = gson.fromJson(doc, PlayerPickACardResponse.class);
//                var i = resp.code();
//            }
//
//            @Override
//            public String placeInThread() {
//                return null;
//            }
//
//            @Override
//            public AcceptedAddresses getEvents() {
//                return new AcceptedAddresses(game.append(PlayerPickACard.KEY));
//            }
//        });
//
//        msg = gson.toJson(table);
//        tunnel.sendDocumentFromServer(game.append(TableChange.KEY), msg);
//        msg = gson.toJson(new PlayerPickACardObj());
//        tunnel.sendDocumentFromServer(game.append(PlayerPickACard.KEY), msg);
//    }
//
//}
