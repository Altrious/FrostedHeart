package com.teammoeg.frostedheart.research;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;

public class FHResearch {
	public static ResearchRegistry researches=new ResearchRegistry();
	public static ClueRegistry clues=new ClueRegistry();
	private static LazyOptional<List<Research>> allResearches=LazyOptional.of(()->researches.all());
	private static LazyOptional<List<AbstractClue>> allClues=LazyOptional.of(()->clues.all());
	public static CompoundNBT save(CompoundNBT cnbt) {
		cnbt.put("clues", clues.serialize());
		cnbt.put("researches", researches.serialize());
		return cnbt;
	}
	public void prepareReload() {
		researches.prepareReload();
		clues.prepareReload();
		allResearches=LazyOptional.of(()->researches.all());
		allClues=LazyOptional.of(()->clues.all());
	}
	public static void load(CompoundNBT cnbt) {
		clues.deserialize(cnbt.getList("clues",0));
		researches.deserialize(cnbt.getList("researches",0));
	}
	public static Supplier<Research> getResearch(String id) {
		return researches.get(id);
	}
	public static Supplier<AbstractClue> getClue(String id) {
		return clues.get(id);
	}
	public static Supplier<Research> getResearch(int id) {
		return researches.get(id);
	}
	public static Supplier<AbstractClue> getClue(int id) {
		return clues.get(id);
	}
	public static List<Research> getAllResearch() {
		return allResearches.resolve().get();
	}
	public static List<Research> getResearchesForRender(ResearchCategory cate){
		List<Research> all= getAllResearch();
		ArrayList<Research> available=new ArrayList<>();
		ArrayList<Research> unlocked=new ArrayList<>();
		for(Research r:all) {
			if(r.getCategory()!=cate)continue;
			if(r.isCompleted())unlocked.add(r);
			else if(r.isUnlocked())available.add(r);
		}
		available.ensureCapacity(available.size()+unlocked.size());
		available.addAll(unlocked);
		return available;
	}
	public static List<AbstractClue> getAllClue() {
		return allClues.resolve().get();
	}
}
