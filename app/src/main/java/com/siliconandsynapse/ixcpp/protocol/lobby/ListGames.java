package com.siliconandsynapse.ixcpp.protocol.lobby;


//import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
        import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

        import java.util.List;

public class ListGames implements IxReceiver
{
	private final AcceptedAddresses events;
	private final RoomModel model;
	private final IxAddress baseAddr;

	public record ListGamesDto(int gameId, String type,
							   int freeSeats, List<ListGamesPlayersObj> players) {}
	public record ListGamesPlayersObj(int seat, String name) {}

	public ListGames(IxAddress baseAddr, RoomModel model, GameController gameManager)
	{
		this.baseAddr = baseAddr;
		this.model = model;

		var addr = baseAddr.append("ListGames");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
        var gson = new Gson();
        var t = new TypeToken<List<ListGamesDto>>(){};
        var games = gson.fromJson(doc, t);

		for (var g: games) {
			var id = g.gameId();
			model.addGame(new GameInfo(id, g.type()), g.players());
		}

	}
	public String placeInThread()
	{
		return baseAddr.toString();
	}
	public AcceptedAddresses getEvents()
	{
		return events;

	}

}
