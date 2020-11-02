package com.codergeezer.auth.entity;

import com.codergeezer.common.data.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author haidv
 * @version 1.0
 */
@Data
@Entity
@Table(name = "account")
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "total_failed")
    private Integer totalFailed;

    @Column(name = "roles")
    private String roles;
}
