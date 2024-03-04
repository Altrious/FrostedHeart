package com.teammoeg.frostedheart.content.scenario;

import java.util.Map;

@FunctionalInterface
public interface TypeAdapter<T,R> {
	public T convert(R runner,String[] parnames,Map<String, String> params);
}