package com.diploma.zoo_hotel.repository;

import com.diploma.zoo_hotel.entities.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    @Query(value = "select * from schedule where sender_id = :senderId", nativeQuery = true)
    List<Schedule> findAllByEmployeeId(@Param(value = "senderId") Long id);

    @Query(value = "delete from schedule where sender_id = :senderId and id = :id", nativeQuery = true)
    void deleteByIds(@Param(value = "id") Long id,@Param(value = "senderId") Long employeeId);
}
