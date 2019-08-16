package ru.shestakova.repository;

import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import ru.shestakova.model.Request;

public class JPARequestRepository implements RequestRepository {

  private static final String PERSISTENCE_UNIT_NAME = "helios";

  private final EntityManagerFactory factory;

//  @PersistenceUnit(name = "helios_pg")
//  @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
//  @Inject
  private EntityManager entityManager;

  public JPARequestRepository() {
    this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    this.entityManager = factory.createEntityManager();
  }

  @Override
  public RepositoryResponse<Void> addRequest(Request request) {
    Objects.requireNonNull(request, "Request should not be null");

    EntityTransaction transaction = entityManager.getTransaction();
    try {
      if (!transaction.isActive()) {
        transaction.begin();
      }

      entityManager.persist(request);
      transaction.commit();

      return RepositoryResponse.getSuccessResponseWith(null);
    } catch (RollbackException | IllegalStateException ex) {
      return RepositoryResponse.getFailResponseWith(ex);
    } catch (Exception ex) {
      transaction.rollback();
      return RepositoryResponse.getFailResponseWith(ex);
    }
  }

  @Override
  public RepositoryResponse<List<Request>> getAllRequests() {
    try {
      Query query = entityManager.createNativeQuery("SELECT * FROM request;", Request.class);
      List<Request> resultList = (List<Request>) query.getResultList();

      return RepositoryResponse.getSuccessResponseWith(resultList);
    } catch (Exception ex) {
      return RepositoryResponse.getFailResponseWith(ex);
    }
  }

  @Override
  public RepositoryResponse<Void> deleteAllRequests() {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      if (!transaction.isActive()) {
        transaction.begin();
      }

      Query query = entityManager.createNativeQuery("DELETE FROM request;");
      query.executeUpdate();
      transaction.commit();

      return RepositoryResponse.getSuccessResponseWith(null);
    } catch (RollbackException | IllegalStateException ex) {
      return RepositoryResponse.getFailResponseWith(ex);
    } catch (Exception ex) {
      transaction.rollback();
      return RepositoryResponse.getFailResponseWith(ex);
    }
  }

  @Override
  public RepositoryResponse<Void> createTable() {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      if (!transaction.isActive()) {
        transaction.begin();
      }

      Query query = entityManager.createNativeQuery(
          "CREATE TABLE IF NOT EXISTS request (\n"
              + "   id     SERIAL NOT NULL,\n"
              + "   x      REAL   NOT NULL,\n"
              + "   y      REAL   NOT NULL,\n"
              + "   r      REAL   NOT NULL,\n"
              + "   result BOOLEAN NOT NULL,\n"
              + "   PRIMARY KEY (id)\n"
              + ");"
      );
      query.executeUpdate();
      transaction.commit();

      return RepositoryResponse.getSuccessResponseWith(null);
    } catch (RollbackException | IllegalStateException ex) {
      return RepositoryResponse.getFailResponseWith(ex);
    } catch (Exception ex) {
      transaction.rollback();
      return RepositoryResponse.getFailResponseWith(ex);
    }
  }

  @Override
  public RepositoryResponse<Void> dropTable() {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      if (!transaction.isActive()) {
        transaction.begin();
      }

      Query query = entityManager.createNativeQuery("DROP TABLE IF EXISTS request;");
      query.executeUpdate();
      transaction.commit();

      return RepositoryResponse.getSuccessResponseWith(null);
    } catch (RollbackException | IllegalStateException ex) {
      return RepositoryResponse.getFailResponseWith(ex);
    } catch (Exception ex) {
      transaction.rollback();
      return RepositoryResponse.getFailResponseWith(ex);
    }
  }
}
