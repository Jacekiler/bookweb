package com.jszarski.bookservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AppUserIdGenerator")
    @SequenceGenerator(name = "AppUserIdGenerator", sequenceName = "app_user_seq", allocationSize = 1)
    private Long id;
    private String username;
    private String password;
    private AppUserRole role;
}
