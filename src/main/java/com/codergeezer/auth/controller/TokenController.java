package com.codergeezer.auth.controller;

import com.codergeezer.common.data.ResponseData;
import com.codergeezer.common.data.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TokenController {

    @GetMapping("/userinfo")
    public ResponseEntity<ResponseData<Object>> getUserInfo(@AuthenticationPrincipal Principal principal) {
        return ResponseUtils.success(principal);
    }
}
