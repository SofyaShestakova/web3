package ru.shestakova.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Entity
@Table(name = "request")
@Data
@Wither
@NoArgsConstructor
@AllArgsConstructor
public class Request {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "x", nullable = false, updatable = false)
  private double x;

  @Column(name = "y", nullable = false, updatable = false)
  private double y;

  @Column(name = "r", nullable = false, updatable = false)
  private double r;

  @Column(name = "result", nullable = false, updatable = false)
  private boolean result;
}
