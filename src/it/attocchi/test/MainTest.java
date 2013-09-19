package it.attocchi.test;

import it.attocchi.utils.DateUtilsLT;

import java.util.Date;

public class MainTest {

	public static void main(String[] args) {
		System.out.println(DateUtilsLT.isBetween(new Date(), new Date(), new Date()));
		System.out.println(DateUtilsLT.isBetween(new Date(), DateUtilsLT.addDays(new Date(), -1), new Date()));
		System.out.println(DateUtilsLT.isBetween(new Date(), DateUtilsLT.addDays(new Date(), -10), DateUtilsLT.addDays(new Date(), 10)));
		System.out.println(DateUtilsLT.isBetween(new Date(), DateUtilsLT.addDays(new Date(), 10), DateUtilsLT.addDays(new Date(), 50)));
	}

}
