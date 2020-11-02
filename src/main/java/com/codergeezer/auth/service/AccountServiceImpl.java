package com.codergeezer.auth.service;

import com.codergeezer.auth.entity.Account;
import com.codergeezer.auth.entity.LockedHistory;
import com.codergeezer.auth.repository.AccountRepository;
import com.codergeezer.auth.repository.LockedHistoryRepository;
import com.codergeezer.common.exception.BusinessException;
import com.codergeezer.auth.enums.ErrorNum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author haidv
 * @version 1.0
 */
@Slf4j
@Service
public class AccountServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final LockedHistoryRepository lockedHistoryRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              LockedHistoryRepository lockedHistoryRepository) {
        this.accountRepository = accountRepository;
        this.lockedHistoryRepository = lockedHistoryRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String input) {
        Account account = accountRepository.findAccountByUserLogin(input)
                                           .orElseThrow(() -> new BusinessException(ErrorNum.USER_NOT_FOUND));
        if (account.getTotalFailed() >= 5) {
            throw new BusinessException(ErrorNum.USER_LOCKED);
        }
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (!StringUtils.isEmpty(account.getRoles())) {
            grantedAuthorities = Arrays.stream(account.getRoles().split(","))
                                       .map(SimpleGrantedAuthority::new)
                                       .collect(Collectors.toList());
        }
        return new User(account.getUsername(), account.getPassword(), grantedAuthorities);
    }

    @EventListener
    public void authFailedEventListener(AbstractAuthenticationFailureEvent failureEvent) {
        // write custom code here login failed audit.
        if (failureEvent.getException() instanceof BadCredentialsException) {
            Account account = accountRepository.findAccountByUserLogin(failureEvent.getAuthentication().getName())
                                               .orElse(null);
            if (account != null) {
                if (account.getTotalFailed() < 5) {
                    account.setTotalFailed(account.getTotalFailed() + 1);
                    accountRepository.save(account);
                }
                if (account.getTotalFailed() == 5) {
                    if (lockedHistoryRepository.findByUsername(account.getUsername()).isPresent()) {
                        LockedHistory lockedHistory = new LockedHistory();
                        lockedHistory.setLockedTime(new Date());
                        lockedHistory.setUsername(account.getUsername());
                        lockedHistory.setActivated(true);
                        lockedHistory.setIsLocked(true);
                        lockedHistoryRepository.save(lockedHistory);
                    }
                }
            }
        }
    }
}
