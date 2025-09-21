package com.siliconandsynapse.aclient.game;

import com.siliconandsynapse.ixcpp.protocol.game.TableChange;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public abstract class CardSorterBase {

    public boolean canSkip(List<TableChange.TableChangeObjStack> stack) {
        var all = stack.stream()
                .allMatch(p ->
                        p.cards().size() == 0 ||
                                "".equals(p.cards().get(0).card().getType())
                );
        return all;
    }

    public List<TableChange.TableChangeObjStack> sort(
            List<TableChange.TableChangeObjStack> stack,
            Hashtable<String, Integer> map
    ) {
        var results =  stack.stream()
                .sorted((a, b) -> {

                    try {
                        Integer va = 0;
                        if (!a.cards().isEmpty()) {
                            var ca = a.cards().get(0);
                            var sa = ca.card().getType() + "-" + ca.card().getSuit();
                            va = map.getOrDefault(sa, 0);

                        }
                        Integer vb = 0;
                        if (!b.cards().isEmpty()) {
                            var cb = b.cards().get(0);
                            var sb = cb.card().getType() + "-" + cb.card().getSuit();
                            vb = map.getOrDefault(sb, 0);
                        }

                        return vb - va;
                    } catch (Exception ex) {
                        return 0;
                    }
                }).toList();
        return results;
    }
    public List<TableChange.TableChangeObjStack> compact(List<TableChange.TableChangeObjStack> stack) {

        var newResults = new ArrayList<TableChange.TableChangeObjStack>();
        var size = stack.size();
        for (int i = 0; i < size; i++)
        {
            var old = stack.get(i);
            var x = new TableChange.TableChangeObjStack(i, old.stackType(), old.cards());
            newResults.add(x);
        }
        return newResults;
    }
}
