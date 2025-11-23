package com.kerem.exception;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIError<T> {

    private String id;

    private LocalDateTime errorTime;

    private T error;
}
