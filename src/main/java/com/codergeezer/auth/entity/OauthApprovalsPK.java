package com.codergeezer.auth.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author haidv
 * @version 1.0
 */
@Data
@Embeddable
public class OauthApprovalsPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "userid")
    private String userId;

    @Column(name = "clientid")
    private String clientId;
}
