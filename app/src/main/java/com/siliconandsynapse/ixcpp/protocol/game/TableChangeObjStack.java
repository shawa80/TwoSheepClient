package com.siliconandsynapse.ixcpp.protocol.game;

import java.util.ArrayList;
import java.util.List;

public record TableChangeObjStack(int id, String stackType, List<TableChangeObjCard> cards) {}
