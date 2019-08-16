package ru.shestakova.repository;

import java.util.List;
import ru.shestakova.model.Request;

public interface RequestRepository {

  RepositoryResponse<Void> addRequest(Request request);

  RepositoryResponse<List<Request>> getAllRequests();

  RepositoryResponse<Void> deleteAllRequests();

  RepositoryResponse<Void> createTable();

  RepositoryResponse<Void> dropTable();
}
