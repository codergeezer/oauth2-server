package com.codergeezer.auth.repository;

import com.codergeezer.auth.entity.OauthScopes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface OauthScopesRepository extends JpaRepository<OauthScopes, Long> {

    List<OauthScopes> findByActivatedTrueAndClientId(String clientId);
}
