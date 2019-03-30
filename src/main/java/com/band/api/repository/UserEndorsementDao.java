package com.band.api.repository;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Getter
@Table(name = "user_endorsement", catalog = "band")
public class UserEndorsementDao {
    @Id
    private Integer id;

    @Column(name = "endorser_user_id", columnDefinition = "VARCHAR(128)", nullable = false)
    private Integer endorserUserId;

    @Column(name = "endorsee_user_id", columnDefinition = "VARCHAR(128)", nullable = false)
    private Integer endorseeUserId;

    @Column(nullable = false)
    private Integer skill;

}
