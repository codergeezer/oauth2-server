package com.codergeezer.auth.repository;

import com.codergeezer.auth.entity.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, String> {

}
