package com.ado.jsonconvert;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("name", "ado");
		System.out.println(JSONObject.toJSON(map));
	}

}
