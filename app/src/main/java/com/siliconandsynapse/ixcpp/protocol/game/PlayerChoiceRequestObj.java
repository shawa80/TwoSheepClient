package com.siliconandsynapse.ixcpp.protocol.game;

import java.util.List;

public record PlayerChoiceRequestObj(String id, String question,
                                     List<PlayerChoiceRequestAnswer> answers) {}
