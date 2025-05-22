package work.test_api_tg.dto;


import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String name;
}