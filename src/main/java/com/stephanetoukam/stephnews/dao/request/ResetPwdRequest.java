package com.stephanetoukam.stephnews.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPwdRequest {
    private String password;
    private String confirmPassword;
}
