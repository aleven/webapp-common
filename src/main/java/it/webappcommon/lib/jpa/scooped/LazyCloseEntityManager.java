package it.webappcommon.lib.jpa.scooped;

import javax.persistence.EntityManager;

/**
 *
 * @author puche
 */
public class LazyCloseEntityManager extends EntityManagerProxy {
  
  private LazyCloseListener listener;
  
  public LazyCloseEntityManager(EntityManager delegate) {
    
    super(delegate);
  }
  
  public void setLazyCloseListener(LazyCloseListener listener) {
    
    this.listener = listener;
  }
  
  public LazyCloseListener getLazyCloseListener() {
    
    return listener;
  }
  
  @Override
  public void close() {
  }
  
  protected void lazyClose() {
    
    super.close();
    if (listener != null) listener.lazilyClosed();
  }
}