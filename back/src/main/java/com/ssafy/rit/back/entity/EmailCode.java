package com.ssafy.rit.back.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(value = "email", timeToLive = 600)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailCode {

    @Id
    private String email;

    private int code;
}
