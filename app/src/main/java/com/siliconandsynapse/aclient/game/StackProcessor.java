package com.siliconandsynapse.aclient.game;

import com.siliconandsynapse.ixcpp.protocol.game.TableChange;

import java.util.List;

public interface StackProcessor {
    List<TableChange.TableChangeObjStack> stackPreprocessor(List<TableChange.TableChangeObjStack> stack);
}
