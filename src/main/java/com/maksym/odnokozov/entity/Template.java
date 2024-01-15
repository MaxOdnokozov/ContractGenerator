package com.maksym.odnokozov.entity;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
import jakarta.validation.constraints.NotNull;
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
@Table(name = "template")
public class Template {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "template_sequence")
  @SequenceGenerator(name = "template_sequence", allocationSize = 1)
  private Long id;

  @NotNull
  @Size(min = 3, max = 64)
  private String name;

  @NotBlank private String filePath;

  @Builder.Default private boolean isActive = false;

  private Language language;

  @ToString.Exclude
  @OneToMany(mappedBy = "template", fetch = FetchType.LAZY)
  private List<Placeholder> placeholders;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private User owner;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "organization_id")
  private Organization organization;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (isNull(o) || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Template template = (Template) o;
    return nonNull(getId()) && Objects.equals(getId(), template.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
