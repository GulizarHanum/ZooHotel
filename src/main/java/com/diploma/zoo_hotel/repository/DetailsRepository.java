package com.diploma.zoo_hotel.repository;

import com.diploma.zoo_hotel.entities.Details;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DetailsRepository extends CrudRepository<Details, Long> {

    @Query(value = "select * from details where employee_id = :employeeId", nativeQuery = true)
    Optional<Details> findByEmployee(@Param(value = "employeeId") Long id);

    @Query(value = "delete from details where employee_id = :employeeId", nativeQuery = true)
    void deleteByEmployee(@Param(value = "employeeId") Long id);
}
