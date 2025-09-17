package com.example.springWorkShop.dao.auth.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponse {

    private Long id;

    private String username;

    private String role;


}
