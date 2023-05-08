package com.diploma.zoo_hotel.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Сущность "refresh-токен"
 */
@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "token", nullable = false, unique = true)
    private UUID token;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

}
