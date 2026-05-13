package com.upc.presulisto.DTO;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String newPassword;
    private String confirmPassword;
}
