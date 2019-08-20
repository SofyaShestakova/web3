package ru.shestakova.bean;

import java.util.Collections;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shestakova.model.Request;
import ru.shestakova.repository.JPARequestRepository;
import ru.shestakova.repository.RepositoryResponse;
import ru.shestakova.repository.RequestRepository;

@ManagedBean(name = "requestsData", eager = true)
@ApplicationScoped
@Data
public class RequestDataBean {

  private static final Logger logger = LoggerFactory.getLogger(RequestDataBean.class);

  private RequestRepository repository;
  private double x;
  private double y;
  private double r;
  private String result;
  private Integer size = 0;

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
      logger.debug("getRequests(): returning list of size {}", requests.size());
      this.size = requests.size();
      return requests;
    } else {
      // todo implement behaviour
      logger.debug("getRequests(): returning empty list");
      this.size = 0;
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
      logger.debug("addRequest(): added {}", request.toString());
      return request;
    } else {
      logger.debug("addRequest(): could not add request: {}", response.getException().getMessage());
      return null;
    }
  }
}

