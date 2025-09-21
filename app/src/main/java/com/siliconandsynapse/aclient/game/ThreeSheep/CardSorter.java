package com.siliconandsynapse.aclient.game.ThreeSheep;

import com.siliconandsynapse.aclient.game.CardSorterBase;
import com.siliconandsynapse.aclient.game.StackProcessor;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.protocol.game.TableChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class CardSorter extends CardSorterBase implements StackProcessor {

    private static Hashtable<String, Integer> map = init();
    @Override
    public List<TableChange.TableChangeObjStack> stackPreprocessor(List<TableChange.TableChangeObjStack> stack) {

        if (canSkip(stack))
            return stack;

        var results = sort(stack, map);
        results = compact(results);

        return results;
    }

    private static Hashtable<String, Integer> init()
    {
        var map = new Hashtable<String, Integer>();

        map.put("Queen-Clubs", 32);
        map.put("Queen-Spades", 31);
        map.put("Queen-Hearts", 30);
        map.put("Queen-Diamonds", 29);

        map.put("Jack-Clubs", 28);
        map.put("Jack-Spades", 27);
        map.put("Jack-Hearts", 26);
        map.put("Jack-Diamonds", 25);

        map.put("ACE-Diamonds", 24);
        map.put("Ten-Diamonds", 23);
        map.put("King-Diamonds", 22);
        map.put("Nine-Diamonds", 21);
        map.put("Eight-Diamonds", 20);
        map.put("Seven-Diamonds", 19);

        map.put("ACE-Clubs", 18);
        map.put("Ten-Clubs", 17);
        map.put("King-Clubs", 16);
        map.put("Nine-Clubs", 15);
        map.put("Eight-Clubs", 14);
        map.put("Seven-Clubs", 13);

        map.put("ACE-Spades", 12);
        map.put("Ten-Spades", 11);
        map.put("King-Spades", 10);
        map.put("Nine-Spades", 9);
        map.put("Eight-Spades", 8);
        map.put("Seven-Spades", 7);

        map.put("ACE-Hearts", 6);
        map.put("Ten-Hearts", 5);
        map.put("King-Hearts", 4);
        map.put("Nine-Hearts", 3);
        map.put("Eight-Hearts", 2);
        map.put("Seven-Hearts", 1);

        return map;
    }
}
