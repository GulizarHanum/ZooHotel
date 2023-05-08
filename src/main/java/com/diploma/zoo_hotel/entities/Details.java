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

    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "experience")
    private Integer experience;

    @ElementCollection
    @CollectionTable(name = "accept_sizes", joinColumns = @JoinColumn(name = "details_id"))
    @Column(name = "accept_size")
    @Type(type = "org.hibernate.type.TextType")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<WeightEnum> acceptSize;

    @Column(name = "is_house")
    private Boolean isHouse;

    @Column(name = "children")
    private Integer children;

    @Column(name = "have_equipment")
    private Boolean haveEquipment;

    @Column(name = "accept_animals")
    private String acceptAnimals;

//    @OneToMany(mappedBy = "details", orphanRemoval = true)
    @ElementCollection
    @CollectionTable(name = "employee_animals", joinColumns = @JoinColumn(name = "details_id"))
    @Column(name = "pet_id")
    @Type(type = "org.hibernate.type.TextType")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Pet> employeeAnimals;

    @Column(name = "have_vet_education")
    private Boolean haveVetEducation;

}
