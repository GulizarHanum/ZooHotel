package com.diploma.zoo_hotel.repository;

import com.diploma.zoo_hotel.entities.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {
}
