package ru.shestakova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import ru.shestakova.model.Request;
import ru.shestakova.repository.JPARequestRepository;
import ru.shestakova.repository.RequestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Database interaction with RequestRepository")
//@Disabled
public class RequestRepositoryTests {

  private RequestRepository requestRepository;

  @BeforeAll
  public void init() {
    requestRepository = new JPARequestRepository();
    requestRepository.dropTable().ensureSuccess();
  }

  @BeforeEach
  public void beforeTest() {
    requestRepository.createTable().ensureSuccess();
  }

  @AfterEach
  public void afterTest() {
    requestRepository.dropTable().ensureSuccess();
  }

  @Test
  @DisplayName("Create and drop table")
  public void createAndDropTableTest() {
    requestRepository.createTable().ensureSuccess();

    List<Request> requests = requestRepository.getAllRequests().ensureSuccess();
    assertTrue(requests.isEmpty());

    requestRepository.dropTable().ensureSuccess();
  }

  @Test
  @DisplayName("Add and get all")
  public void addRequestTests() {
    final int requestsAmount = 10;
    List<Request> generatedRequests = Stream.generate(RequestRepositoryTests::generateRandomRequest)
        .limit(requestsAmount)
        .peek(request -> requestRepository.addRequest(request).ensureSuccess())
        .collect(Collectors.toList());

    List<Request> databaseRequests = requestRepository.getAllRequests().ensureSuccess();
    assertEquals(generatedRequests.size(), databaseRequests.size());
    assertTrue(generatedRequests.containsAll(databaseRequests));
  }

  @Test
  @DisplayName("Add and delete all")
  public void deleteAllTest() {
    final int requestsAmount = 3;
    Stream.generate(RequestRepositoryTests::generateRandomRequest)
        .limit(requestsAmount)
        .forEach(request -> requestRepository.addRequest(request).ensureSuccess());

    requestRepository.deleteAllRequests().ensureSuccess();

    List<Request> databaseRequests = requestRepository.getAllRequests().ensureSuccess();
    assertTrue(databaseRequests.isEmpty());
  }

  public static Request generateRandomRequest() {
    return new Request()
        .withX(10 * Math.random())
        .withY(10 * Math.random())
        .withR(100 * Math.random())
        .withResult(Math.random() > 0.5);
  }
}
