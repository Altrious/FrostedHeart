package com.teammoeg.frostedheart.research;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

public abstract class FHRegistry<T extends FHRegisteredItem> {
	private ArrayList<T> items=new ArrayList<>();
	private List<String> rnames=new ArrayList<>();
	public FHRegistry() {
		
	}
	public void register(T item) {
		if(item.getRId()==0) {
			String lid=item.getLId();
			int index=rnames.indexOf(lid);
			ensure();
			if(index==-1) {
				item.setRId(rnames.size()+1);
				items.add(item);
				rnames.add(item.getLId());
			}else {
				items.set(index,item);
			}
		}
	}
	public T getById(int id) {
		return items.get(id-1);
	}
	public T getByName(String lid){
		int index=rnames.indexOf(lid);
		if(index!=-1)
			return items.get(index);
		return null;
	}
	public void ensure() {
		items.ensureCapacity(rnames.size());
		while(items.size()<rnames.size())
			items.add(null);
	}
	ListNBT serialize() {
		ListNBT cn=new ListNBT();
		rnames.stream().map(StringNBT::valueOf).forEach(e->cn.add(e));
		return cn;
	}
	void deserialize(ListNBT load) {
		rnames.clear();
		load.stream().map(INBT::getString).forEach(e->rnames.add(e));
		ensure();
	}
	public int getSize() {
		return rnames.size();
	}
}