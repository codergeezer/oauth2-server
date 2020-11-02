package com.codergeezer.auth.config;

import com.codergeezer.auth.service.OauthClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

/**
 * @author haidv
 * @version 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfigAdapter extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final OauthClientDetailsServiceImpl clientDetailsService;

    private final DataSource dataSource;

    @Value("${security.oauth2.authorization.jwt.key-alias}")
    private String keyAlias;

    @Value("${security.oauth2.authorization.jwt.key-password}")
    private String keyPassword;

    @Value("${security.oauth2.authorization.jwt.key-store}")
    private String keyStore;

    @Autowired
    public AuthServerConfigAdapter(AuthenticationManager authenticationManager,
                                   OauthClientDetailsServiceImpl clientDetailsService,
                                   DataSource dataSource) {
        this.authenticationManager = authenticationManager;
        this.clientDetailsService = clientDetailsService;
        this.dataSource = dataSource;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()")
                   .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                 .authenticationManager(authenticationManager)
                 .accessTokenConverter(accessTokenConverter())
                 .reuseRefreshTokens(true)
                 .approvalStore(approvalStore());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource(keyStore),
                                                                  keyPassword.toCharArray()).getKeyPair(keyAlias));
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }
}
