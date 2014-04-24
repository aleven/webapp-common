package it.attocchi.test;

import java.util.Arrays;

public class TestList {

	public static void main(String[] args) {
		System.out.println(Arrays.asList("mirco".split("\\s*,\\s*")));
		System.out.println(Arrays.asList("mirco,ciao".split("\\s*,\\s*")));
		System.out.println(Arrays.asList("mirco,ciao, test".split("\\s*,\\s*")));
	}

}
