package com.teammoeg.frostedheart.research.clues;

import com.teammoeg.frostedheart.research.clues.AbstractClue;
import net.minecraft.util.text.ITextComponent;

public class RootClue extends AbstractClue {
	public RootClue(String ID, float contribution, ITextComponent name, ITextComponent desc, ITextComponent hint,boolean pend) {
		super(ID, contribution, name, desc, hint,pend);
	}

}