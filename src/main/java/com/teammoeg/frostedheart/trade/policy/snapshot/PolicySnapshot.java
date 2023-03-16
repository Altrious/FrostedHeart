package com.teammoeg.frostedheart.trade.policy.snapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teammoeg.frostedheart.trade.FHVillagerData;
import com.teammoeg.frostedheart.trade.policy.BaseData;

public class PolicySnapshot {
	public static final PolicySnapshot empty=new PolicySnapshot() {
		@Override
		public void register(BaseData bd) {}
		@Override
		public void calculateRecovery(int deltaDays,FHVillagerData data) {}
		@Override
		public void fetchTrades(Map<String, Float> data) {}
	};
	Map<String,BaseData> data=new HashMap<>();
	List<BuyData> buys=new ArrayList<>();
	Map<String,SellData> sells=new HashMap<>();
	public int maxExp;
	public void register(BaseData bd) {
		data.put(bd.getId(), bd);
	}
	public void calculateRecovery(int deltaDays,FHVillagerData data) {
		this.data.values().forEach(t->t.tick(deltaDays, data));
	}
	public void fetchTrades(Map<String,Float> data){
		this.data.values().forEach(t->t.fetch(this,data));
	}
	public void registerBuy(BuyData bd) {
		getBuys().add(bd);
	}
	public void registerSell(SellData sd) {
		getSells().put(sd.getId(), sd);
	}
	@Override
	public String toString() {
		return "PolicySnapshot [data=" + data + ", buys=" + getBuys() + ", sells=" + getSells() + "]";
	}
	public Map<String,SellData> getSells() {
		return sells;
	}
	public List<BuyData> getBuys() {
		return buys;
	}
}
