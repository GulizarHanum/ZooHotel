package com.diploma.zoo_hotel.controller;

import com.diploma.zoo_hotel.dto.FeedbackDto;
import com.diploma.zoo_hotel.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@Tag(name = "Отзывы", description = "API для работы с отзывами Профиля")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping(path = "/public/feedbacks/{id}")
    @Operation(description = "Получить отзывы по айди профиля")
    public List<FeedbackDto> getFeedbackByRecipientId(@Parameter(description = "Идентификатор профиля") @PathVariable Long id) {
        return feedbackService.getFeedbacksByRecipientId(id);
    }

    @GetMapping(path = "/public/feedbacks/{id}/avg")
    @Operation(description = "Получить среднюю оценку отзывов по айди профиля")
    public BigDecimal getFeedbackAvgMarkByRecipientId(@Parameter(description = "Идентификатор профиля") @PathVariable Long id) {
        return feedbackService.getFeedbackAvgMarkByRecipientId(id);
    }

    @PostMapping("/feedbacks")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать отзыв")
    public void createFeedback(@Parameter(description = "Данные отзыва") @RequestBody @Valid FeedbackDto dto) {
        feedbackService.createFeedback(dto);
    }

    @DeleteMapping("/feedbacks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить отзыв")
    public void deleteFeedback(@Parameter(description = "Идентификатор отзыва") @PathVariable Long id) {
        feedbackService.deleteFeedback(id);
    }
}
