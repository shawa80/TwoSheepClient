package com.siliconandsynapse.aclient.game.Euchre;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.ixcpp.common.cards.Card;

import java.util.List;

public class DiscardAdapter extends ArrayAdapter<Card> {

    private final Activity context;
    private final List<Card> cards;

    public DiscardAdapter(Activity context, List<Card> cards) {
        super(context, R.layout.card_image_discard_list_item, cards);

        this.context=context;
        this.cards = cards;

    }

    public View getView(int position, View view, ViewGroup parent) {
        var inflater = context.getLayoutInflater();
        var rowView=inflater.inflate(R.layout.card_image_discard_list_item, null,true);

        var titleText = (TextView) rowView.findViewById(R.id.title);
        var imageView = (ImageView) rowView.findViewById(R.id.icon);
        var subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        var card = cards.get(position);
        titleText.setText(card.getType() + " of " + card.getSuit());
        var image = Images.getPokerImage(context, card, false);
        imageView.setImageDrawable(image);
        subtitleText.setText("");

        return rowView;
    };


}
