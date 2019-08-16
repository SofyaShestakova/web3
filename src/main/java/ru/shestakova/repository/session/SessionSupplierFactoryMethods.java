package ru.shestakova.repository.session;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import java.util.Properties;
import ru.shestakova.util.HibernateSessionFactory;

public class SessionSupplierFactoryMethods {

  public static SessionSupplier getJSCHForwaredHibernateSessionSupplier(String sshHost, String host,
      int port, int lport, int rport, int timeout,
      String user, String password) {
    if (sshHost == null) {
      throw new IllegalArgumentException("SSH host should be provided through jsch.sshHost");
    }

    if (host == null) {
      throw new IllegalArgumentException("Host should be provided through jsch.host");
    }

    if (user == null) {
      throw new IllegalArgumentException("SSH user should be provided through jsch.user");
    }

    try {
      JSch jsch = new JSch();
      com.jcraft.jsch.Session session = jsch.getSession(user, sshHost, port);

      if (password != null) {
        session.setPassword(password);
      }

      Properties config = new Properties();
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);

      session.connect(timeout);
      session.setPortForwardingL(lport, host, rport);
    } catch (JSchException ex) {
      ex.printStackTrace();
    }

    return HibernateSessionFactory::getSession;
  }

  public static SessionSupplier getHibernateSessionSupplier() {
    return HibernateSessionFactory::getSession;
  }
}
