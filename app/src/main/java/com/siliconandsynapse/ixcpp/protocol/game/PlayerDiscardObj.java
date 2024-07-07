package com.siliconandsynapse.ixcpp.protocol.game;

import com.siliconandsynapse.ixcpp.common.cards.Card;

import java.util.List;

public record PlayerDiscardObj (int min, int max, String message, List<Integer> codes) {}

