package com.example.todo_api.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")

    private Long id;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_password")
    private String password;

}
