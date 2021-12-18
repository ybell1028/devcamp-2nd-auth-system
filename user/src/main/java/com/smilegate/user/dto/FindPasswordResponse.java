package com.smilegate.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordResponse {
    private String decrypted;

    public static FindPasswordResponse toDto(String decrypted) {
        return FindPasswordResponse
                .builder()
                .decrypted(decrypted)
                .build();
    }
}
