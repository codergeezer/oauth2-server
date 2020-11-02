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
@Table(name = "oauth_scopes")
public class OauthScopes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "oauth_scopes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String oauthScopesId;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "scope")
    private String scope;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_at")
    private Date lastModifiedAt;

    @Column(name = "is_activated")
    private boolean activated;
}
