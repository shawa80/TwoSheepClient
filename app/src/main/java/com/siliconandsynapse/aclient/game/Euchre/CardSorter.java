package com.siliconandsynapse.aclient.game.Euchre;

import com.siliconandsynapse.aclient.game.CardSorterBase;
import com.siliconandsynapse.aclient.game.StackProcessor;
import com.siliconandsynapse.aclient.gameModels.PlayerModel;
import com.siliconandsynapse.aclient.gameModels.models.UpdateUser;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.types.PokerCard;
import com.siliconandsynapse.ixcpp.protocol.game.TableChange;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CardSorter extends CardSorterBase implements StackProcessor, UpdateUser {

    private int suit = 0;
    private volatile String x = "";

    private Hashtable<String, Integer> map = init(0);
    @Override
    public List<TableChange.TableChangeObjStack> stackPreprocessor(List<TableChange.TableChangeObjStack> stack) {

        map = init(suit);

        if (canSkip(stack))
            return stack;

        var results = sort(stack, map);
        results = compact(results);

        return results;
    }

    private Hashtable<String, Integer> init(int suit)
    {
        var map = new Hashtable<String, Integer>();

        int jackClubBonus = 0;
        int jackSpaceBonus = 0;
        int jackHeartBonus = 0;
        int jackDiamondBonus = 0;

        int hearts = 0;
        if (suit == PokerCard.HEART) {
            hearts = 100;
            jackHeartBonus = 100;
            jackDiamondBonus = 50;
        }
        int clubs = 0;
        if (suit == PokerCard.CLUB) {
            clubs = 100;
            jackClubBonus = 100;
            jackSpaceBonus = 50;
        }
        int diamonds = 0;
        if (suit == PokerCard.DIAMOND) {
            diamonds = 100;
            jackDiamondBonus = 100;
            jackHeartBonus = 50;
        }
        int spades = 0;
        if (suit == PokerCard.SPADE) {
            spades = 100;
            jackSpaceBonus = 100;
            jackClubBonus = 50;
        }


        map.put("ACE-Clubs", 24 + clubs);
        map.put("King-Clubs", 23 + clubs);
        map.put("Queen-Clubs", 22 + clubs);
        map.put("Jack-Clubs", 21 + clubs + jackClubBonus + jackSpaceBonus);
        map.put("Ten-Clubs", 20 + clubs);
        map.put("Nine-Clubs", 19 + clubs);

        map.put("ACE-Spades", 18 + spades);
        map.put("King-Spades", 17 + spades);
        map.put("Queen-Spades", 16 + spades);
        map.put("Jack-Spades", 15 + spades + jackClubBonus + jackSpaceBonus);
        map.put("Ten-Spades", 14 + spades);
        map.put("Nine-Spades", 13 + spades);

        map.put("ACE-Hearts", 12 + hearts);
        map.put("King-Hearts", 11 + hearts);
        map.put("Queen-Hearts", 10 + hearts);
        map.put("Jack-Hearts", 9 + hearts + jackHeartBonus + jackDiamondBonus);
        map.put("Ten-Hearts", 8 + hearts);
        map.put("Nine-Hearts", 7 + hearts);

        map.put("ACE-Diamonds", 6 + diamonds);
        map.put("King-Diamonds", 5 + diamonds);
        map.put("Queen-Diamonds", 4 + diamonds);
        map.put("Jack-Diamonds", 3 + diamonds + jackDiamondBonus + jackHeartBonus);
        map.put("Ten-Diamonds", 2 + diamonds);
        map.put("Nine-Diamonds", 1 + diamonds);

        return map;
    }

    @Override public void turnChanged(PlayerModel player) {}

    @Override public void nameChanged(PlayerModel player, String name) {}
    @Override public void scoreChanged(PlayerModel player, int score) {}
    @Override public void wealthChanged(PlayerModel player, int wealth) {}

    @Override
    public void descriptionChanged(PlayerModel player, String description) {
        x = description;

        if (description.contains("♥")) {
            suit = PokerCard.HEART;
        }
        if (description.contains("♦")) {
            suit = PokerCard.DIAMOND;
        }
        if (description.contains("♠")) {
            suit = PokerCard.SPADE;
        }
        if (description.contains("♣")) {
            suit = PokerCard.CLUB;
        }
    }
}
