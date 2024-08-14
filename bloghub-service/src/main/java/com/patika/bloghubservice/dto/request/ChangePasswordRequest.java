package com.patika.bloghubservice.dto.request;

import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        String email,
        String oldPassword,
        @Size(min = 6, max = 12, message = "Parola min 6 max 12 karakterden oluşmalıdır") String newPassword
) {
}
