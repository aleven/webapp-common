package it.webappcommon.lib.jpa.scooped;

import javax.persistence.EntityManagerFactory;

/**
 * A scooped persistence manager for JSF
 * @author puche
 */
public class ScopedPersistenceManager extends PersistenceManager {
  
  protected ScopedPersistenceManager() {
  }
  
  protected EntityManagerFactory createEntityManagerFactory() {
    
    return new ScopedEntityManagerFactory(super.createEntityManagerFactory());
  }
}