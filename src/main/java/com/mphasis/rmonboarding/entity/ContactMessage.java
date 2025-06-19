package com.mphasis.rmonboarding.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_us")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 150)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "email_type",
            nullable = false,
            columnDefinition = "ENUM('leads') DEFAULT 'leads'"
    )
    private EmailType emailType = EmailType.leads;

    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    public enum EmailType {
        leads
    }
}

