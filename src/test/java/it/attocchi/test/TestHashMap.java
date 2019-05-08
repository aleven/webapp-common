package it.attocchi.test;

import java.util.HashMap;
import java.util.Map;

public class TestHashMap {
	public static void main(String[] args) {
		Map<String, Double> t = new HashMap<String, Double>();
		t.put("a", 1d);
		t.put("b", 2d);
		t.put("a", 3d);

		for (String a : t.keySet()) {
			System.out.println(a + "=" + t.get(a));
		}
	}
}
