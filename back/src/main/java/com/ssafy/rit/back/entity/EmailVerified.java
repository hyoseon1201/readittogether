package com.ssafy.rit.back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Table(name = "email_verified")
public class EmailVerified {

    @Id
    @Column(name = "code", nullable = false)
    private int code;

    @Column(name = "email", nullable = false)
    private String email;
}
