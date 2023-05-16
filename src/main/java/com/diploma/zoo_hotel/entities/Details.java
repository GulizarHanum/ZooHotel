package com.diploma.zoo_hotel.entities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "details")
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "experience")
    private Integer experience;

    @ElementCollection
    @CollectionTable(name = "accept_sizes", joinColumns = @JoinColumn(name = "details_id"))
    @Column(name = "accept_size")
    @EqualsAndHashCode.Exclude
    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    private List<WeightEnum> acceptSize;

    @Column(name = "is_house")
    private Boolean isHouse;

    @Column(name = "children")
    private Integer children;

    @Column(name = "have_equipment")
    private Boolean haveEquipment;

    @ElementCollection
    @CollectionTable(name="accept_animals", joinColumns = @JoinColumn(name = "details_id"))
    @Column(name = "accept_animal")
    @Enumerated(EnumType.STRING)
    private List<AnimalType> acceptAnimals;

    @Column(name = "have_animals")
    private Boolean haveAnimals;

    @Column(name = "have_vet_education")
    private Boolean haveVetEducation;

}
