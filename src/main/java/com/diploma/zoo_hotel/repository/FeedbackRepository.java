package com.diploma.zoo_hotel.repository;

import com.diploma.zoo_hotel.entities.Feedback;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Интерфейс для работы с таблицей отзывов в БД
 */
@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

    @Query(value = "select * from feedback where recipient_id = :recipientId", nativeQuery = true)
    List<Feedback> findByRecipient(@Param(value = "recipientId") Long id);

}
