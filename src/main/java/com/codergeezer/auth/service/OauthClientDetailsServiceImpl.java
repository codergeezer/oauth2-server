package com.codergeezer.auth.service;

import com.codergeezer.auth.entity.OauthClientDetails;
import com.codergeezer.auth.repository.OauthClientDetailsRepository;
import com.codergeezer.auth.repository.OauthScopesRepository;
import com.codergeezer.common.utils.JsonUtils;
import com.codergeezer.auth.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author haidv
 * @version 1.0
 */
@Slf4j
@Service
public class OauthClientDetailsServiceImpl implements ClientDetailsService {

    private final OauthClientDetailsRepository oauthClientDetailsRepository;

    private final OauthScopesRepository oauthScopesRepository;

    @Autowired
    public OauthClientDetailsServiceImpl(OauthClientDetailsRepository oauthClientDetailsRepository,
                                         OauthScopesRepository oauthScopesRepository) {
        this.oauthClientDetailsRepository = oauthClientDetailsRepository;
        this.oauthScopesRepository = oauthScopesRepository;
    }

    /**
     * Load a client by the client id. This method must not return null.
     *
     * @param clientId The client id.
     * @return The client details (never null).
     * @throws ClientRegistrationException If the client account is locked, expired, disabled, or invalid for any other
     *                                     reason.
     */
    @Override
    @Cacheable(value = "load-client-by-id", key = "#clientId")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetails oauthClientDetails = oauthClientDetailsRepository
                .findById(clientId)
                .orElseThrow(() -> new NoSuchClientException("No client with requested id: " + clientId));
        String scopes = oauthScopesRepository.findByActivatedTrueAndClientId(clientId)
                                             .stream()
                                             .map(tmp -> tmp.getScope().trim())
                                             .collect(Collectors.joining(","));
        scopes = StringUtils.isEmpty(scopes) ? Constant.OAUTH2_DEFAULT_SCOPE : scopes;
        BaseClientDetails details = new BaseClientDetails(oauthClientDetails.getClientId(),
                                                          oauthClientDetails.getResourceIds(),
                                                          scopes,
                                                          oauthClientDetails.getAuthorizedGrantTypes(),
                                                          oauthClientDetails.getAuthorities(),
                                                          oauthClientDetails.getWebServerRedirectUri());
        details.setClientSecret(oauthClientDetails.getClientSecret());
        if (oauthClientDetails.getAccessTokenValiditySeconds() != null) {
            details.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValiditySeconds());
        }
        if (oauthClientDetails.getRefreshTokenValiditySeconds() != null) {
            details.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValiditySeconds());
        }
        String json = oauthClientDetails.getAdditionalInformation();
        if (json != null) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> additionalInformation = JsonUtils.fromJson(json, Map.class);
                details.setAdditionalInformation(additionalInformation);
            } catch (Exception e) {
                log.warn("Could not decode JSON for additional information: " + details, e);
            }
        }
        String autoApprove = oauthClientDetails.getAutoApprove();
        if (autoApprove != null) {
            details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(autoApprove));
        }
        return details;
    }
}
