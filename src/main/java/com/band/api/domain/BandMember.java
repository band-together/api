package com.band.api.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@Getter
@Table(name = "band_member", catalog = "band")
public class BandMember implements Serializable {
    @Id
    @Column(name = "band_id", nullable = false)
    private Integer bandId;

    @Id
    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Id
    @Column(columnDefinition = "VARCHAR(128)")
    private String role;

    @Column(name = "joined_date", columnDefinition = "DATE", nullable = false)
    private Date joinedDate;


}
