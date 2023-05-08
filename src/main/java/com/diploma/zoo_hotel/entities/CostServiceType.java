package com.diploma.zoo_hotel.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "cost_service_type")
public class CostServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "employee_id")
    private Long employeeId;
    @Column(name = "service_type")
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
    @Column(name = "cost")
    private BigDecimal cost;
}
