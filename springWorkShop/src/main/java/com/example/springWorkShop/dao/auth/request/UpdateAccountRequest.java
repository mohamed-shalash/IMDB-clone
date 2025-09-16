package com.example.springWorkShop.dao.auth.request;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class UpdateAccountRequest {
    private Long id;

    private String userName;

    private String password;


    private String role;
}
