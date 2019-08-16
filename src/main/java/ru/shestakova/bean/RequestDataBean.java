package ru.shestakova.bean;

import java.util.Collections;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import lombok.Data;
import ru.shestakova.model.Request;
import ru.shestakova.repository.JPARequestRepository;
import ru.shestakova.repository.RepositoryResponse;
import ru.shestakova.repository.RequestRepository;

@ManagedBean(name = "requestsData", eager = true)
@ApplicationScoped
@Data
public class RequestDataBean {

  private RequestRepository repository;
  private double x;
  private double y;
  private double r;
  private String result;

  public RequestDataBean() {
    this(new JPARequestRepository());
  }

  public RequestDataBean(RequestRepository repository) {
    this.repository = repository;

    RepositoryResponse<Void> response = repository.createTable();
    if (!response.isSuccess()) {
      if (response.getException() != null) {
        throw new RuntimeException(
            "Could not create table during creation of request data bean: " + response
                .getException().getMessage(), response.getException());
      } else {
        throw new RuntimeException("Could not create table during creation of request data bean");
      }
    }
  }

  public List<Request> getRequests() {
    RepositoryResponse<List<Request>> response = repository.getAllRequests();

    if (response.isSuccess()) {
      List<Request> requests = response.getResult();
      Collections.reverse(requests);
      return requests;
      //return requests.subList(Math.max(0, requests.size() - 10), requests.size()); // todo normalize
    } else {
      // todo implement behaviour
      return Collections.emptyList();
    }
  }

  public Request addRequest() {
    boolean res = Boolean.parseBoolean(result);
    Request request = new Request()
        .withX(x)
        .withY(y)
        .withR(r)
        .withResult(res);

    RepositoryResponse<Void> response = repository.addRequest(request);
    if (response.isSuccess()) {
      System.out.println("Added " + request.toString());
      return request;
    } else {
      System.out.println("Could not add given request: " + response.getException().getMessage());
      return null;
    }
  }
}

