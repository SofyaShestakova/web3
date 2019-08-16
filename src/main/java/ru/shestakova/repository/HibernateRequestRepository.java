package ru.shestakova.repository;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import ru.shestakova.model.Request;
import ru.shestakova.repository.session.SessionSupplier;
import ru.shestakova.repository.session.SessionSupplierFactoryMethods;

public class HibernateRequestRepository implements RequestRepository {

  private SessionSupplier supplier;

  public HibernateRequestRepository() {
    if (Boolean.valueOf(getSystemProperty("jsch.tunnel", "false"))) {
      supplier = SessionSupplierFactoryMethods.getJSCHForwaredHibernateSessionSupplier(
          getSystemProperty("jsch.sshHost", null),
          getSystemProperty("jsch.host", null),
          Integer.parseInt(getSystemProperty("jsch.port", "22")),
          Integer.parseInt(getSystemProperty("jsch.lport", "5432")),
          Integer.parseInt(getSystemProperty("jsch.rport", "5432")),
          Integer.parseInt(getSystemProperty("jsch.timeout", "10000")),
          getSystemProperty("jsch.user", null),
          getSystemProperty("jsch.password", null)
      );
    } else {
      supplier = SessionSupplierFactoryMethods.getHibernateSessionSupplier();
    }
  }

  @Override
  public RepositoryResponse<Void> addRequest(Request request) {
    Session sessionObject = supplier.supplySession();

    try {
      Transaction transactionObject = sessionObject.beginTransaction();
      sessionObject.save(request);

      transactionObject.commit();
      return RepositoryResponse.getSuccessResponseWith(null);
    } catch (Exception ex) {
      ex.printStackTrace();
      return RepositoryResponse.getFailResponseWith(ex);
    } finally {
      sessionObject.close();
    }
  }

  @Override
  public RepositoryResponse<List<Request>> getAllRequests() {
    try (Session sessionObject = supplier.supplySession()) {
      NativeQuery sqlQuery = sessionObject.createSQLQuery("SELECT * FROM request;");
      sqlQuery.addEntity(Request.class);

      List requestObjects = sqlQuery.list();
      List<Request> requests = new ArrayList<>(requestObjects.size());

      for(Object requestObject : requestObjects) {
        requests.add((Request) requestObject);
      }

      return RepositoryResponse.getSuccessResponseWith(requests);
    } catch (Exception ex) {
      ex.printStackTrace();
      return RepositoryResponse.getFailResponseWith(ex);
    }
  }

  @Override
  public RepositoryResponse<Void> deleteAllRequests() {
    Session sessionObject = supplier.supplySession();

    try {
      Transaction transactionObject = sessionObject.beginTransaction();
      SQLQuery query = sessionObject.createSQLQuery("DELETE FROM request");
      query.executeUpdate();

      if (transactionObject != null) {
        transactionObject.commit();
      }

      return RepositoryResponse.getSuccessResponseWith(null);
    } catch (Exception ex) {
      ex.printStackTrace();
      return RepositoryResponse.getFailResponseWith(ex);
    } finally {
      sessionObject.close();
    }
  }

  @Override
  public RepositoryResponse<Void> createTable() {
    Session sessionObject = supplier.supplySession();

    try {
      Transaction transactionObject = sessionObject.beginTransaction();
      SQLQuery query = sessionObject.createSQLQuery("create table if not exists REQUEST (\n"
          + "   id     SERIAL NOT NULL,\n"
          + "   x      REAL   NOT NULL,\n"
          + "   y      REAL   NOT NULL,\n"
          + "   r      REAL   NOT NULL,\n"
          + "   result BOOLEAN NOT NULL,\n"
          + "   PRIMARY KEY (id)\n"
          + ");");
      query.executeUpdate();

      if (transactionObject != null) {
        transactionObject.commit();
      }

      return RepositoryResponse.getSuccessResponseWith(null);
    } catch (Exception ex) {
      ex.printStackTrace();
      return RepositoryResponse.getFailResponseWith(ex);
    } finally {
      sessionObject.close();
    }
  }

  @Override
  public RepositoryResponse<Void> dropTable() {
    Session sessionObject = supplier.supplySession();

    try {
      Transaction transactionObject = sessionObject.beginTransaction();
      SQLQuery query = sessionObject.createSQLQuery("drop table if exists REQUEST");
      query.executeUpdate();

      if (transactionObject != null) {
        transactionObject.commit();
      }

      return RepositoryResponse.getSuccessResponseWith(null);
    } catch (Exception ex) {
      ex.printStackTrace();
      return RepositoryResponse.getFailResponseWith(ex);
    } finally {
      sessionObject.close();
    }
  }

  private static String getSystemProperty(String propName, String defaultValue) {
    return System.getProperty(propName, defaultValue);
  }
}
