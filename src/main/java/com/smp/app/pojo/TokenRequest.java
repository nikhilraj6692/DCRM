package com.smp.app.pojo;

import com.smp.app.util.SMPAppConstants;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {

    @NotBlank(message = SMPAppConstants.INVALID_REFRESH_TOKEN)
    private String refreshToken;
}
