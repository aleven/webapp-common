package it.webappcommon.lib.jpa.scooped;

import javax.persistence.EntityManager;

/**
 * <p>LazyCloseEntityManager class.</p>
 *
 * @author puche
 * @version $Id: $Id
 */
public class LazyCloseEntityManager extends EntityManagerProxy {
  
  private LazyCloseListener listener;
  
  /**
   * <p>Constructor for LazyCloseEntityManager.</p>
   *
   * @param delegate a {@link javax.persistence.EntityManager} object.
   */
  public LazyCloseEntityManager(EntityManager delegate) {
    
    super(delegate);
  }
  
  /**
   * <p>setLazyCloseListener.</p>
   *
   * @param listener a {@link it.webappcommon.lib.jpa.scooped.LazyCloseListener} object.
   */
  public void setLazyCloseListener(LazyCloseListener listener) {
    
    this.listener = listener;
  }
  
  /**
   * <p>getLazyCloseListener.</p>
   *
   * @return a {@link it.webappcommon.lib.jpa.scooped.LazyCloseListener} object.
   */
  public LazyCloseListener getLazyCloseListener() {
    
    return listener;
  }
  
  /** {@inheritDoc} */
  @Override
  public void close() {
  }
  
  /**
   * <p>lazyClose.</p>
   */
  protected void lazyClose() {
    
    super.close();
    if (listener != null) listener.lazilyClosed();
  }
}
