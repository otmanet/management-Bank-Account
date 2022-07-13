package com.example.demo.ConfigSecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Set<String> roles= AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if(roles.contains("ROLE_ADMIN")){
             response.sendRedirect("/client/index");
        }else{
            response.sendRedirect("/GestionAccount");
        }
    }
}
