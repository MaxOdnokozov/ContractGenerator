package com.maksym.odnokozov.entity;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "placeholder_value")
public class PlaceholderValue {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "placeholder_value_sequence")
  @SequenceGenerator(name = "placeholder_value_sequence", allocationSize = 1)
  private Long id;

  @NotBlank private String value;

  private Integer sequenceNumber;

  @ManyToOne private Placeholder placeHolder;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (isNull(o) || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    PlaceholderValue placeholderValue = (PlaceholderValue) o;
    return nonNull(getId()) && Objects.equals(getId(), placeholderValue.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}