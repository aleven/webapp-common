package it.webappcommon.lib.jpa.scooped;

import javax.persistence.EntityManagerFactory;

/**
 * A scooped persistence manager for JSF
 *
 * @author puche
 * @version $Id: $Id
 */
public class ScopedPersistenceManager extends PersistenceManager {
  
  /**
   * <p>Constructor for ScopedPersistenceManager.</p>
   */
  protected ScopedPersistenceManager() {
  }
  
  /**
   * <p>createEntityManagerFactory.</p>
   *
   * @return a {@link javax.persistence.EntityManagerFactory} object.
   */
  protected EntityManagerFactory createEntityManagerFactory() {
    
    return new ScopedEntityManagerFactory(super.createEntityManagerFactory());
  }
}
