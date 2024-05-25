package com.siliconandsynapse.ixcpp.common;

import org.w3c.dom.*;

import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.CardFactory;

import javax.xml.parsers.*;
import javax.xml.xpath.*;


public class DiscardSerialize
{
	public static Document encode(Discard d)
	{

		Document doc;
		Element discard;
		Element cards;
		Element card;
		Card c;
		DocumentBuilder documentBuilder = null;

		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (Exception e) {
		}

		doc = documentBuilder.newDocument();
		discard = doc.createElement("discard");
		discard.setAttribute("min", "" + d.getMin());
		discard.setAttribute("max", "" + d.getMax());
		discard.setAttribute("message", d.getMessage());
		doc.appendChild(discard);

		cards = doc.createElement("cards");
		discard.appendChild(cards);

		for (int i = 0; i < d.getCount() ; i++)
		{
			c = d.getCardAt(i);

			card = doc.createElement("card");

			card.setAttribute("id", c.getCode() + "");
			if (c.getFace() == Card.UP)
			{
				card.setAttribute("type", c.getType());
				card.setAttribute("suit", c.getSuit());
			}
			else
			{
				card.setAttribute("type", "*");
				card.setAttribute("suit", "*");
			}
			cards.appendChild(card);
		}

		return doc;
	}

	public static Discard decode(CardFactory cache, Document doc)
	{
		Discard discard = new Discard();
		int min = 0;
		int max = 0;
		String message = "";
		NodeList nodes = null;
		Element card;
		String id;
		int secureCode;

		XPath path;

		path = XPathFactory.newInstance().newXPath();

		try {
			min = Integer.parseInt(path.evaluate("/discard/@min", doc));
			max = Integer.parseInt(path.evaluate("/discard/@max", doc));
			message = path.evaluate("/discard/@message", doc);

			nodes = (NodeList)path.evaluate("/discard/cards/card", doc, XPathConstants.NODESET);

		} catch (Exception e) {

		}

		discard.setMax(max);
		discard.setMin(min);
		discard.setMessage(message);

			
		for (int i = 0; i < nodes.getLength(); i++)
		{
			card = (Element)nodes.item(i);
			id = card.getAttribute("id");
			try {
				secureCode = Integer.parseInt(id);
				discard.addCard(cache.getCardById(secureCode));

			} catch (NumberFormatException e) {

			}
		}

		return discard;
	}


}	
