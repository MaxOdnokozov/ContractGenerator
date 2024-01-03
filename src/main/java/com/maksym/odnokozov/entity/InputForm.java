package com.maksym.odnokozov.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "input_form")
public class InputForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InputFormType type;

    private String message;

    @ElementCollection
    @CollectionTable(name = "input_form_default_values", joinColumns = @JoinColumn(name = "input_form_id"))
    @Column(name = "value")
    private List<String> defaultValues;

    @OneToOne(mappedBy = "inputForm")
    private Placeholder placeholder;
}
