package com.example.springWorkShop.dao.auth.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateAccountResponse {
    private Long id;

    private String userName;

    private String role;

    private  String token;

    public  UpdateAccountResponse(Long id, String userName, String role) {
        this.id = id;
        this.userName = userName;
        this.role = role;
    }
}
