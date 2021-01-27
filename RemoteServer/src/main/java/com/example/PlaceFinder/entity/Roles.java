package com.example.PlaceFinder.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Roles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRole;

    private String username;
    private String role;

    /*@OneToOne(mappedBy = "role")
    private User user;*/
}