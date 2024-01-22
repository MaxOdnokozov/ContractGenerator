package com.maksym.odnokozov.entity;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "placeholder")
public class Placeholder {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "placeholder_sequence")
  @SequenceGenerator(name = "placeholder_sequence", allocationSize = 1)
  private Long id;

  @NotBlank
  @Size(min = 1, max = 128)
  private String name;

  @Size(min = 1, max = 128)
  private String description;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "template_id")
  private Template template;

  @Builder.Default private PlaceholderType type = PlaceholderType.TEXT;

  @ToString.Exclude
  @OneToMany(
      mappedBy = "placeholder",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<PlaceholderValue> predefinedValues;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (isNull(o) || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Placeholder placeholder = (Placeholder) o;
    return nonNull(getId()) && Objects.equals(getId(), placeholder.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
