package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.EmployeeDto;
import com.diploma.zoo_hotel.dto.FeedbackDto;
import com.diploma.zoo_hotel.entities.Customer;
import com.diploma.zoo_hotel.entities.Employee;
import com.diploma.zoo_hotel.entities.Feedback;
import com.diploma.zoo_hotel.repository.FeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Логика для работы с отзывами
 */
@Service
@AllArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    /**
     * Создать отзыв
     *
     * @param dto объект с данными
     */
    public void createFeedback(FeedbackDto dto) {
        Customer sender = customerService.getCustomerById(dto.getSenderId());
        Employee recipient = employeeService.getEmployeeById(dto.getRecipientId());
        if (Objects.equals(sender.getId(), recipient.getId())) {
            throw new IllegalArgumentException("Вы не можете оценить себя.");
        }

        Feedback feedback = new Feedback();
        feedback.setId(dto.getId());
        feedback.setMark(new BigDecimal(dto.getMark()));
        feedback.setRecipient(recipient);
        feedback.setText(dto.getText());
        feedback.setSender(sender);
        feedback.setSendDateTime(LocalDateTime.now());
        feedbackRepository.save(feedback);

        Long recipientId = recipient.getId();
        BigDecimal newAvgMark = this.getFeedbackAvgMarkByRecipientId(recipientId);
        employeeService.updateAvgMark(recipientId, newAvgMark);
    }

    /**
     * Получить список отзывов по получателю
     *
     * @param recipientId айди получателя
     *
     * @return список отзывов
     */
    public List<FeedbackDto> getFeedbacksByRecipientId(Long recipientId) {
        if (recipientId == null) {
            throw new IllegalArgumentException("Неверный идентификатор получателя");
        }
        return feedbackRepository.findByRecipient(recipientId)
                .stream()
                .map(FeedbackService::buildDto)
                .collect(Collectors.toList());
    }

    /**
     * Получить среднюю оценку профиля по его отзывам
     *
     * @param recipientId айди профиля
     *
     * @return средняя оценка
     */
    public BigDecimal getFeedbackAvgMarkByRecipientId(Long recipientId) {
        if (recipientId == null) {
            throw new IllegalArgumentException("Неверный идентификатор получателя");
        }

        double avgMark = this.getFeedbacksByRecipientId(recipientId)
                .stream()
                .mapToInt(FeedbackDto::getMark)
                .average()
                .orElse(0);
        return new BigDecimal(avgMark);
    }

    /**
     * Удалить отзыв
     *
     * @param id айди отзыва
     */
    public void deleteFeedback(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор отзыва");
        }
        Feedback feedback = feedbackRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Отзыв с id = %s не найден", id)));
        Long recipientId = feedback.getRecipient().getId();

        feedbackRepository.deleteById(id);

        BigDecimal newAvgMark = this.getFeedbackAvgMarkByRecipientId(recipientId);
        employeeService.updateAvgMark(recipientId, newAvgMark);
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param feedback сущность Отзыв
     *
     * @return ДТО с данными
     */
    private static FeedbackDto buildDto(Feedback feedback) {
        return FeedbackDto.builder()
                .id(feedback.getId())
                .mark(feedback.getMark().intValue())
                .text(feedback.getText())
                .recipientId(feedback.getRecipient().getId())
                .senderId(feedback.getSender().getId())
                .sendDateTime(feedback.getSendDateTime())
                .build();
    }
}
