 package com.psp.test.controller;

import com.psp.test.auth.TokenHandler;
import com.psp.test.auth.model.SecurityContextService;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


 @RestController
 @RequestMapping("/api/movie/auth")
 public class AuthController {

     private final AuthenticationManager authenticationManager;
     private final TokenHandler tokenHandler;
     private final SecurityContextService securityContextService;

     @Autowired
     AuthController(AuthenticationManager authenticationManager,
                    TokenHandler tokenHandler,
                    SecurityContextService securityContextService) {
         this.authenticationManager = authenticationManager;
         this.tokenHandler = tokenHandler;
         this.securityContextService = securityContextService;
     }

     @RequestMapping(method = RequestMethod.POST)
     public AuthResponse auth(@RequestBody AuthParams params) throws AuthenticationException {
         final UsernamePasswordAuthenticationToken loginToken = params.toAuthenticationToken();
         final Authentication authentication = authenticationManager.authenticate(loginToken);
         SecurityContextHolder.getContext().setAuthentication(authentication);

         return securityContextService.currentUser().map(u -> {
             final String token = tokenHandler.createTokenForUser(u);
             return new AuthResponse(token);
         }).orElseThrow(RuntimeException::new);


     }

     @Value
     private static final class AuthParams {
         private final String email;
         private final String password;

         UsernamePasswordAuthenticationToken toAuthenticationToken() {
             return new UsernamePasswordAuthenticationToken(email, password);
         }
     }

     @Value
     private static final class AuthResponse {
         private final String token;
     }

 }
