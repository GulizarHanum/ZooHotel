package com.diploma.zoo_hotel.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "photo")
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "name")
    private String name;

    @Column(name = "animal_type")
    @Enumerated(EnumType.STRING)
    private AnimalType animalType;

    @Column(name = "have_all_vaccinations")
    private Boolean haveAllVaccinations;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "isFemale")
    private Boolean isFemale;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "pet", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Property> properties;
}
