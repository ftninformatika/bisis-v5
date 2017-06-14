package com.ftninformatika.bisis.rest_service.bisis4_model;

public class HibernateUtil {

 /* private static final SessionFactory sessionFactory;
  private static final ThreadLocal threadSession =new ThreadLocal();
  private static final ThreadLocal threadTransaction = new ThreadLocal();
  private static Log log = LogFactory.getLog(HibernateUtil.class.getName()); 
  
  static {
    try {
      //INIFile iniFile = BisisApp.getINIFile();
      INIFile iniFile = new INIFile(HibernateUtil.class.getResource("/client-config.ini"));
      Configuration cfg = new Configuration().configure(HibernateUtil.class.getResource(
        "/com/gint/app/bisis4/client/circ/model/hibernate.cfg.xml"));
      cfg.setProperty("hibernate.connection.driver_class", iniFile.getString("database", "driver"));
      cfg.setProperty("hibernate.connection.url", iniFile.getString("database", "url"));
      cfg.setProperty("hibernate.connection.username", iniFile.getString("database", "username"));
      cfg.setProperty("hibernate.connection.password", iniFile.getString("database", "password"));
      sessionFactory = cfg.buildSessionFactory();
    } catch (Throwable ex) {
      
      throw new ExceptionInInitializerError(ex);
    }
  }
  
  public static Session getSession() {
    Session s = (Session) threadSession.get();
    if (s == null) {
        s = sessionFactory.openSession();
        s.setFlushMode(FlushMode.COMMIT);
        threadSession.set(s);
    }
    return s;
  }
    
  public static void closeSession() {
    Session s = (Session) threadSession.get();
    threadSession.set(null);
    if (s != null && s.isOpen())
      s.close();
  }
    
  public static void beginTransaction() {
    Transaction tx = (Transaction) threadTransaction.get();
    if (tx == null) {
      tx = getSession().beginTransaction();
      threadTransaction.set(tx);
    }
  }
  
  public static void commitTransaction() {
    Transaction tx = (Transaction) threadTransaction.get();
    if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() )
      tx.commit();
    threadTransaction.set(null);
  }
      
  public static void rollbackTransaction() {
    Transaction tx = (Transaction) threadTransaction.get();
    threadTransaction.set(null);
    if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() ) {
      tx.rollback();
    }
    closeSession();
  }*/
}
