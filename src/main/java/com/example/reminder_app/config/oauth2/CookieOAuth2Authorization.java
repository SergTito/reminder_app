package com.example.reminder_app.config.oauth2;

import com.example.reminder_app.config.service.CookieUtil;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieOAuth2Authorization implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtil.getCookie(request, "OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME")
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            CookieUtil.deleteCookie(request, response, "OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME");
            CookieUtil.deleteCookie(request, response, "REDIRECT_URI_PARAM_COOKIE_NAME");
            return;
        }
        CookieUtil.addCookie(response,
                "OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME", CookieUtil.serialize(authorizationRequest), 180);
        String redirectUriAfterLogin = request.getParameter("REDIRECT_URI_PARAM_COOKIE_NAME");
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtil.addCookie(response, "REDIRECT_URI_PARAM_COOKIE_NAME", redirectUriAfterLogin, 180);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }


}
