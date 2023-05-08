package com.diploma.zoo_hotel.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private String value;
}
