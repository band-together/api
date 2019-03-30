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
@Table(name = "band", catalog = "band")
public class BandDao {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descritption;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String city;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String state;

}
