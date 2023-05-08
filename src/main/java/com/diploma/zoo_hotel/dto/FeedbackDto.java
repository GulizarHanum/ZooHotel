package com.diploma.zoo_hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@Validated
@Schema(description = "Данные об отзыве")
public class FeedbackDto {

    @Schema(description = "Идентификатор отзыва")
    private Long id;

    @Schema(description = "Идентификатор профиля отправителя")
    private Long senderId;

    @Schema(description = "Идентификатор профиля получателя")
    private Long recipientId;

    @Schema(description = "Текст")
    private String text;

    @Schema(description = "Дата и время отправления")
    private LocalDateTime sendDateTime;

    @NotNull
    @Min(0)
    @Max(5)
    @Schema(description = "Оценка")
    private Integer mark;
}
