package com.diploma.zoo_hotel.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Table(name = "employee")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class  Employee extends Person {

    @OneToMany(mappedBy = "recipient", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Feedback> feedbacks;

    @Column(name = "rating")
    private BigDecimal rating;

//    @Column(name = "details")
//    @Enumerated(EnumType.STRING)
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "details_id", nullable = false)
    private Details details;

    @OneToMany(mappedBy = "sender", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Schedule> schedule;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "employee_id")
    private List<CostServiceType> costs;

    @ElementCollection
    @CollectionTable(name = "employee_photos", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "photo")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<byte[]> photos;

    @OneToMany(mappedBy = "seller", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Order> orders;

    @Column(name = "description")
    private String description;

}
