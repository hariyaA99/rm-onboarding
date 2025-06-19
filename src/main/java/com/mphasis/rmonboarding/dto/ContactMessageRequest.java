package com.mphasis.rmonboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageRequest {
    private String name;
    private String email;
    private String subject;
    private String message;
}
