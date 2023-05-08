package com.diploma.zoo_hotel.repository;

import com.diploma.zoo_hotel.entities.CostServiceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostServiceTypeRepository extends CrudRepository<CostServiceType, Long> {

    @Query(value = "select * from cost_service_type where employee_id = :employeeId", nativeQuery = true)
    List<CostServiceType> findByEmployee(@Param(value = "employeeId") Long id);

    @Query(value = "select * from cost_service_type where employee_id = :employeeId and id = :id", nativeQuery = true)
    CostServiceType findByEmployee(@Param(value = "employeeId") Long employeeId, @Param(value = "id") Long id);

    @Query(value = "delete from cost_service_type where employee_id = :employeeId", nativeQuery = true)
    void deleteByEmployee(@Param(value = "employeeId") Long id);
}
