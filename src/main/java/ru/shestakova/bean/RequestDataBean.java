package ru.shestakova.bean;

import java.util.Collections;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shestakova.model.Request;
import ru.shestakova.repository.JPARequestRepository;
import ru.shestakova.repository.RepositoryResponse;
import ru.shestakova.repository.RequestRepository;

@ManagedBean(name = "requestsData", eager = true)
@ApplicationScoped
@Data @Slf4j
public class RequestDataBean {

  @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
  private RequestRepository repository;

  private double x, y, r;
  private String result;
  private Integer size = 0;

  public RequestDataBean() {
    this(new JPARequestRepository());
  }

  public RequestDataBean(RequestRepository repository) {
    this.repository = repository;
  }

  public List<Request> getRequests() {
    RepositoryResponse<List<Request>> response = repository.getAllRequests();

    if (response.isSuccess()) {
      List<Request> requests = response.getResult();
      Collections.reverse(requests);
      log.debug("getRequests(): returning list of size {}", requests.size());
      this.size = requests.size();
      return requests;
    } else {
      // todo implement behaviour
      log.debug("getRequests(): returning empty list");
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
      log.debug("addRequest(): added {}", request.toString());
      return request;
    } else {
      log.debug("addRequest(): could not add request: {}", response.getException().getMessage());
      return null;
    }
  }
}

