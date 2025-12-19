package com.kerem.exception;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIError<T> {

    private String id;

    private LocalDateTime errorTime;

    private T error;
}
