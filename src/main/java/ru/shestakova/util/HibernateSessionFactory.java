package ru.shestakova.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions, tied to the current thread of execution.
 * Follows the Thread Local Session pattern, see {
 *
 * @link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

  /**
   * Location of hibernate.cfg.xml file. Location should be on the classpath as Hibernate uses
   * #resourceAsStream style lookup for its configuration file. The default classpath location of
   * the hibernate config file is in the default package. Use #setConfigFile() to update the
   * location of the configuration file for the current session.
   */
  private static final String CONFIG_FILE_LOCATION = "hibernate.cfg.xml";
  private static final ThreadLocal<Session> LOCAL_SESSION = new ThreadLocal<>();

  private static Configuration configuration =
      new Configuration(new MetadataSources().addResource("hibernate.cfg.xml"));
  private static String configFile = CONFIG_FILE_LOCATION;

  private static SessionFactory sessionFactory;

  static {
    configure();
  }

  private HibernateSessionFactory() {
  }

  /**
   * Returns the ThreadLocal Session instance.  Lazy initialize the <code>SessionFactory</code> if
   * needed.
   *
   * @return Session
   */
  public static Session getSession() throws HibernateException {
    Session session = LOCAL_SESSION.get();
    if (session == null || !session.isOpen()) {

      if (sessionFactory == null) {
        rebuildSessionFactory();
      }

      session = (sessionFactory != null)
          ? sessionFactory.openSession()
          : null;

      LOCAL_SESSION.set(session);
    }

    return session;
  }

  /**
   * Rebuild hibernate session factory
   */
  public static void rebuildSessionFactory() {
    configure();
  }

  private static void configure() {
    try {
      configuration.addResource(configFile);
      configuration.configure();

      /*ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
      builder.applySettings(configuration.getProperties());*/
//      ServiceRegistry serviceRegistry = builder.buildServiceRegistry();

      sessionFactory = configuration.buildSessionFactory();
//      sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    } catch (Exception e) {
      System.err.println("%%%% Error Creating SessionFactory %%%%");
      e.printStackTrace();
    }
  }

  /**
   * Close the single hibernate session instance.
   */
  public static void closeSession() throws HibernateException {
    Session session = LOCAL_SESSION.get();
    LOCAL_SESSION.set(null);

    if (session != null) {
      session.close();
    }
  }

  /**
   * return session factory
   */
  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  /**
   * return session factory
   * <p>
   * session factory will be rebuilded in the next call
   */
  public static void setConfigFile(String configFile) {
    HibernateSessionFactory.configFile = configFile;
    sessionFactory = null;
  }

  /**
   * return hibernate configuration
   */
  public static Configuration getConfiguration() {
    return configuration;
  }


}
