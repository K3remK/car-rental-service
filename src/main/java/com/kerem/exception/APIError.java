package com.kerem.exception;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIError<T> {

    private String id;

    private Date errorTime;

    private T error;
}
