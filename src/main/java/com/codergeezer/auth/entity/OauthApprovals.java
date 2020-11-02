package com.codergeezer.auth.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author haidv
 * @version 1.0
 */
@Data
@Entity
@Table(name = "oauth_approvals")
public class OauthApprovals implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OauthApprovalsPK id;

    @Column(name = "scope")
    private String scope;

    @Column(name = "expiresat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    @Column(name = "lastmodifiedat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt;
}
