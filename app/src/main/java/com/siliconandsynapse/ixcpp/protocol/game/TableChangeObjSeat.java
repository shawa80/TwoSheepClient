package com.siliconandsynapse.ixcpp.protocol.game;

import java.util.ArrayList;
import java.util.List;

public record TableChangeObjSeat(int playerId, List<TableChangeObjStack> stacks) { }
