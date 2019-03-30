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
@Table(name = "band_skills_needed", catalog = "band")
public class BandSkillsNeedeedDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "band_id", nullable = false)
    private Integer bandId;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String skill;

    @Column( nullable = false)
    private Integer experience;

}
