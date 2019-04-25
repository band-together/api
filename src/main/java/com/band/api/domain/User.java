package com.band.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", catalog = "band")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String emailDisplay;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false, unique = true)
    private String emailSearch;

    @Column(name = "password_hash", columnDefinition = "CHAR(60)", nullable = false)
    private String passwordHash;

    @Column(name = "recovery_hash", columnDefinition = "CHAR(60)")
    private String recoveryHash;

    @Column(name = "recovery_question", columnDefinition = "VARCHAR(128)")
    private String recoveryQuestion;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(128)")
    private String state;

    @Column(columnDefinition = "VARCHAR(128)")
    private String city;

}
