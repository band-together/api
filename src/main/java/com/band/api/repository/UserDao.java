package com.band.api.repository;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Getter
@Table(name = "user", catalog = "band")
public class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String username;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String email;

    @Column(name = "password_hash", columnDefinition = "CHAR(60)", nullable = false)
    private String passwordHash;

    @Column(name = "recovery_hash", columnDefinition = "CHAR(60)")
    private String recoveryHash;

    @Column(name = "recovery_question", columnDefinition = "VARCHAR(128)")
    private String recoveryQuestion;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String state;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String city;

}
