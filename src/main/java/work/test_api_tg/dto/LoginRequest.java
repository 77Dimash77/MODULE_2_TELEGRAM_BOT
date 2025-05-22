package work.test_api_tg.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}