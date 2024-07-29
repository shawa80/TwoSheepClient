package com.siliconandsynapse.aclient.game;

import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.types.PokerCard;

public class Images {

	private static Drawable back;
	private static Drawable back_hz;
	
	private static Hashtable<Integer,Drawable> clubCache = new Hashtable<Integer,Drawable>();
	private static Hashtable<Integer,Drawable> spadeCache = new Hashtable<Integer,Drawable>();
	private static Hashtable<Integer,Drawable> heartCache = new Hashtable<Integer,Drawable>();
	private static Hashtable<Integer,Drawable> diamondCache = new Hashtable<Integer,Drawable>();
	

	@SuppressLint("UseCompatLoadingForDrawables")
	private static Drawable getDrawable(Activity act, int id) {
		return act.getResources()
				.getDrawable(id, null);
	}
	public static void loadCache(Activity act) {
		
		back = getDrawable(act, R.drawable.back);
		back_hz = getDrawable(act, R.drawable.back_hz);
		
		clubCache.clear();
		clubCache.put(PokerCard.ACE, getDrawable(act, R.drawable.poker_clubs_ace));
		clubCache.put(PokerCard.TWO, getDrawable(act, R.drawable.poker_clubs_two));
		clubCache.put(PokerCard.THREE, getDrawable(act, R.drawable.poker_clubs_three));
		clubCache.put(PokerCard.FOUR, getDrawable(act, R.drawable.poker_clubs_four));
		clubCache.put(PokerCard.FIVE, getDrawable(act, R.drawable.poker_clubs_five));
		clubCache.put(PokerCard.SIX, getDrawable(act, R.drawable.poker_clubs_six));
		clubCache.put(PokerCard.SEVEN, getDrawable(act, R.drawable.poker_clubs_seven));
		clubCache.put(PokerCard.EIGHT, getDrawable(act, R.drawable.poker_clubs_eight));
		clubCache.put(PokerCard.NINE, getDrawable(act, R.drawable.poker_clubs_nine));
		clubCache.put(PokerCard.TEN, getDrawable(act, R.drawable.poker_clubs_ten));
		clubCache.put(PokerCard.JACK, getDrawable(act, R.drawable.poker_clubs_jack));
		clubCache.put(PokerCard.QUEEN, getDrawable(act, R.drawable.poker_clubs_queen));
		clubCache.put(PokerCard.KING, getDrawable(act, R.drawable.poker_clubs_king));
		
		spadeCache.clear();
		spadeCache.put(PokerCard.ACE, getDrawable(act, R.drawable.poker_spades_ace));
		spadeCache.put(PokerCard.TWO, getDrawable(act, R.drawable.poker_spades_two));
		spadeCache.put(PokerCard.THREE, getDrawable(act, R.drawable.poker_spades_three));
		spadeCache.put(PokerCard.FOUR, getDrawable(act, R.drawable.poker_spades_four));
		spadeCache.put(PokerCard.FIVE, getDrawable(act, R.drawable.poker_spades_five));
		spadeCache.put(PokerCard.SIX, getDrawable(act, R.drawable.poker_spades_six));
		spadeCache.put(PokerCard.SEVEN, getDrawable(act, R.drawable.poker_spades_seven));
		spadeCache.put(PokerCard.EIGHT, getDrawable(act, R.drawable.poker_spades_eight));
		spadeCache.put(PokerCard.NINE, getDrawable(act, R.drawable.poker_spades_nine));
		spadeCache.put(PokerCard.TEN, getDrawable(act, R.drawable.poker_spades_ten));
		spadeCache.put(PokerCard.JACK, getDrawable(act, R.drawable.poker_spades_jack));
		spadeCache.put(PokerCard.QUEEN, getDrawable(act, R.drawable.poker_spades_queen));
		spadeCache.put(PokerCard.KING, getDrawable(act, R.drawable.poker_spades_king));
		
		heartCache.clear();
		heartCache.put(PokerCard.ACE, getDrawable(act, R.drawable.poker_hearts_ace));
		heartCache.put(PokerCard.TWO, getDrawable(act, R.drawable.poker_hearts_two));
		heartCache.put(PokerCard.THREE, getDrawable(act, R.drawable.poker_hearts_three));
		heartCache.put(PokerCard.FOUR, getDrawable(act, R.drawable.poker_hearts_four));
		heartCache.put(PokerCard.FIVE, getDrawable(act, R.drawable.poker_hearts_five));
		heartCache.put(PokerCard.SIX, getDrawable(act, R.drawable.poker_hearts_six));
		heartCache.put(PokerCard.SEVEN, getDrawable(act, R.drawable.poker_hearts_seven));
		heartCache.put(PokerCard.EIGHT, getDrawable(act, R.drawable.poker_hearts_eight));
		heartCache.put(PokerCard.NINE, getDrawable(act, R.drawable.poker_hearts_nine));
		heartCache.put(PokerCard.TEN, getDrawable(act, R.drawable.poker_hearts_ten));
		heartCache.put(PokerCard.JACK, getDrawable(act, R.drawable.poker_hearts_jack));
		heartCache.put(PokerCard.QUEEN, getDrawable(act, R.drawable.poker_hearts_queen));
		heartCache.put(PokerCard.KING, getDrawable(act, R.drawable.poker_hearts_king));
		
		diamondCache.clear();
		diamondCache.put(PokerCard.ACE, getDrawable(act, R.drawable.poker_diamonds_ace));
		diamondCache.put(PokerCard.TWO, getDrawable(act, R.drawable.poker_diamonds_two));
		diamondCache.put(PokerCard.THREE, getDrawable(act, R.drawable.poker_diamonds_three));
		diamondCache.put(PokerCard.FOUR, getDrawable(act, R.drawable.poker_diamonds_four));
		diamondCache.put(PokerCard.FIVE, getDrawable(act, R.drawable.poker_diamonds_five));
		diamondCache.put(PokerCard.SIX, getDrawable(act, R.drawable.poker_diamonds_six));
		diamondCache.put(PokerCard.SEVEN, getDrawable(act, R.drawable.poker_diamonds_seven));
		diamondCache.put(PokerCard.EIGHT, getDrawable(act, R.drawable.poker_diamonds_eight));
		diamondCache.put(PokerCard.NINE, getDrawable(act, R.drawable.poker_diamonds_nine));
		diamondCache.put(PokerCard.TEN, getDrawable(act, R.drawable.poker_diamonds_ten));
		diamondCache.put(PokerCard.JACK, getDrawable(act, R.drawable.poker_diamonds_jack));
		diamondCache.put(PokerCard.QUEEN, getDrawable(act, R.drawable.poker_diamonds_queen));
		diamondCache.put(PokerCard.KING, getDrawable(act, R.drawable.poker_diamonds_king));
	}
	
	
	public static Drawable getPokerImage(Activity act, Card card) {
		return getPokerImage(act, card, false);
	}
	
	public static Drawable getPokerImage(Activity act, Card card, boolean horiz) {

		
		return getDrawable(act, getPokerImageResource(card, horiz));

	}
	

	private static int getPokerImageResource(Card card, boolean horiz) {
		
		if (card.getFace() == Card.Facing.DOWN && horiz)
			return R.drawable.back_hz;
		
		if (card.getFace() == Card.Facing.DOWN)
			return R.drawable.back;
		
		if (card.getSuitType() == -1 || card.getValueType() == -1)
			return R.drawable.back;
		
		
		
		if (card.getSuitType() == PokerCard.CLUB)
		{
			switch (card.getValueType()) {
				case PokerCard.ACE:
					return R.drawable.poker_clubs_ace;
				case PokerCard.KING:
					return R.drawable.poker_clubs_king;
				case PokerCard.QUEEN:
					return R.drawable.poker_clubs_queen;
				case PokerCard.JACK:
					return R.drawable.poker_clubs_jack;
				case PokerCard.TEN:
					return R.drawable.poker_clubs_ten;
				case PokerCard.NINE:
					return R.drawable.poker_clubs_nine;
				case PokerCard.EIGHT:
					return R.drawable.poker_clubs_eight;
				case PokerCard.SEVEN:
					return R.drawable.poker_clubs_seven;
				case PokerCard.SIX:
					return R.drawable.poker_clubs_six;
				case PokerCard.FIVE:
					return R.drawable.poker_clubs_five;
				case PokerCard.FOUR:
					return R.drawable.poker_clubs_four;
				case PokerCard.THREE:
					return R.drawable.poker_clubs_three;
				case PokerCard.TWO:
					return R.drawable.poker_clubs_two;
			}
		}
		if (card.getSuitType() == PokerCard.SPADE)
		{
			switch (card.getValueType()) {
				case PokerCard.ACE:
					return R.drawable.poker_spades_ace;
				case PokerCard.KING:
					return R.drawable.poker_spades_king;
				case PokerCard.QUEEN:
					return R.drawable.poker_spades_queen;
				case PokerCard.JACK:
					return R.drawable.poker_spades_jack;
				case PokerCard.TEN:
					return R.drawable.poker_spades_ten;
				case PokerCard.NINE:
					return R.drawable.poker_spades_nine;
				case PokerCard.EIGHT:
					return R.drawable.poker_spades_eight;
				case PokerCard.SEVEN:
					return R.drawable.poker_spades_seven;
				case PokerCard.SIX:
					return R.drawable.poker_spades_six;
				case PokerCard.FIVE:
					return R.drawable.poker_spades_five;
				case PokerCard.FOUR:
					return R.drawable.poker_spades_four;
				case PokerCard.THREE:
					return R.drawable.poker_spades_three;
				case PokerCard.TWO:
					return R.drawable.poker_spades_two;
			}
		}
		if (card.getSuitType() == PokerCard.HEART)
		{
			switch (card.getValueType()) {
				case PokerCard.ACE:
					return R.drawable.poker_hearts_ace;
				case PokerCard.KING:
					return R.drawable.poker_hearts_king;
				case PokerCard.QUEEN:
					return R.drawable.poker_hearts_queen;
				case PokerCard.JACK:
					return R.drawable.poker_hearts_jack;
				case PokerCard.TEN:
					return R.drawable.poker_hearts_ten;
				case PokerCard.NINE:
					return R.drawable.poker_hearts_nine;
				case PokerCard.EIGHT:
					return R.drawable.poker_hearts_eight;
				case PokerCard.SEVEN:
					return R.drawable.poker_hearts_seven;
				case PokerCard.SIX:
					return R.drawable.poker_hearts_six;
				case PokerCard.FIVE:
					return R.drawable.poker_hearts_five;
				case PokerCard.FOUR:
					return R.drawable.poker_hearts_four;
				case PokerCard.THREE:
					return R.drawable.poker_hearts_three;
				case PokerCard.TWO:
					return R.drawable.poker_hearts_two;
			}
		}
		if (card.getSuitType() == PokerCard.DIAMOND)
		{
			switch (card.getValueType()) {
				case PokerCard.ACE:
					return R.drawable.poker_diamonds_ace;
				case PokerCard.KING:
					return R.drawable.poker_diamonds_king;
				case PokerCard.QUEEN:
					return R.drawable.poker_diamonds_queen;
				case PokerCard.JACK:
					return R.drawable.poker_diamonds_jack;
				case PokerCard.TEN:
					return R.drawable.poker_diamonds_ten;
				case PokerCard.NINE:
					return R.drawable.poker_diamonds_nine;
				case PokerCard.EIGHT:
					return R.drawable.poker_diamonds_eight;
				case PokerCard.SEVEN:
					return R.drawable.poker_diamonds_seven;
				case PokerCard.SIX:
					return R.drawable.poker_diamonds_six;
				case PokerCard.FIVE:
					return R.drawable.poker_diamonds_five;
				case PokerCard.FOUR:
					return R.drawable.poker_diamonds_four;
				case PokerCard.THREE:
					return R.drawable.poker_diamonds_three;
				case PokerCard.TWO:
					return R.drawable.poker_diamonds_two;
			}
		}
		return -1;
	}

	
}
