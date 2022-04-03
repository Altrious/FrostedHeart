package com.teammoeg.frostedheart.research.effects;

import com.teammoeg.frostedheart.client.util.GuiUtils;

import java.util.ArrayList;

/**
 * Effect on numerical stats of the team's machines or abilities
 */
public class EffectStats extends Effect {

    public EffectStats() {
    }

    @Override
    public void init() {
        name = GuiUtils.translateGui("effect.use");
        tooltip = new ArrayList<>();
        tooltip.add(GuiUtils.translateTooltip("effect.use.1"));
    }

    @Override
    public void grant() {

    }

    @Override
    public void revoke() {

    }
}