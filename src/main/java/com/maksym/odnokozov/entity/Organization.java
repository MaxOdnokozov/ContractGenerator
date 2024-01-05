package com.maksym.odnokozov.entity;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
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
@Table(name = "organization")
public class Organization {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_sequence")
  @SequenceGenerator(name = "organization_sequence", allocationSize = 1)
  private Long id;

  @NotNull
  @Size(min = 3, max = 256)
  private String name;

  @Size(min = 1, max = 256)
  private String description;

  @OneToMany private List<Template> templates;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (isNull(o) || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Organization organization = (Organization) o;
    return nonNull(getId()) && Objects.equals(getId(), organization.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
