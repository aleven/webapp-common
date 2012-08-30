package it.espressoft.jpa2;

/**
 * Usage in web.xml:
 * 
 *   <listener>
 *   	<listener-class>it.espressoft.jpa2.JPASessionListener</listener-class>
 *   </listener>
 * 
 * 
 * 
 * @author Mirco Attocchi
 *
 */
public interface IJpaListernes {
	
	public static final String DEFAULT_PU = "DEFAULT_PU";
	
	public static final String SESSION_EMF = "emf";
	
}
