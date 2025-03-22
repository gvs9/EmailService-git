package com.example.emailservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {

    private String from;
    private String to;
    private String subject;
    private String body;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
