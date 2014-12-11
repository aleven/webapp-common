package it.attocchi.test;

import it.attocchi.test.jpa2.entities.CopyOfGruppo;
import it.attocchi.test.jpa2.entities.Gruppo;
import it.attocchi.test.jpa2.entities.Utente;

public class TestJPA2Entities {

	public static void main(String[] args) {

		Gruppo g = new Gruppo(1);
		Utente u = new Utente(1);

		Gruppo g1 = new Gruppo(2);
		Gruppo g2 = new Gruppo(2);

		CopyOfGruppo cg2 = new CopyOfGruppo(2);

		System.out.println(g.equals(u));
		System.out.println(g.equals(g1));

		System.out.println(g1.equals(g2));
		System.out.println(g1.equals(cg2));

	}

}
