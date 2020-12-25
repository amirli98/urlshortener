package org.kodluyoruz.urlshortener.controller;

import lombok.Getter;
import org.kodluyoruz.urlshortener.model.LoginToken;
import org.kodluyoruz.urlshortener.model.User;
import org.kodluyoruz.urlshortener.service.LoginTokenService;
import org.kodluyoruz.urlshortener.util.Constants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController
{
    @Getter
    private final LoginTokenService loginTokenService;

    protected AbstractController(LoginTokenService loginTokenService) {
        this.loginTokenService = loginTokenService;
    }

    protected final ServletRequestAttributes getCurrentRequestAttributes()
    {
        RequestAttributes attr = RequestContextHolder.getRequestAttributes();
        return attr instanceof ServletRequestAttributes ? (ServletRequestAttributes) attr : null;
    }

    protected final HttpServletRequest getCurrentRequest()
    {
        ServletRequestAttributes attr = getCurrentRequestAttributes();
        return attr == null ? null : attr.getRequest();
    }

    protected final HttpServletResponse getCurrentResponse()
    {
        ServletRequestAttributes attr = getCurrentRequestAttributes();
        return attr == null ? null : attr.getResponse();
    }

    private Cookie getCookie(Cookie[] cookies,String name)
    {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName() == name)
                return cookie;
        }
        return null;
    }
    protected final LoginToken createAndSetAnonymousLoginToken()
    {
        HttpServletResponse response = getCurrentResponse();
        LoginToken lt  = getLoginTokenService().addAnonymousLoginToken();

        Cookie cookie = new Cookie(Constants.LOGIN_TOKEN_COOKIE_NAME, lt.getValue() );
        cookie.setMaxAge((int)(lt.getExpirationDuration()/1000L));
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return lt;
    }

    protected final LoginToken getLoginToken()
    {
        HttpServletRequest request = getCurrentRequest();
        Cookie[] cookies = request.getCookies();
        Cookie cookie = getCookie(cookies, Constants.LOGIN_TOKEN_COOKIE_NAME);

        LoginToken loginToken = null;
        if (cookie == null) {
            loginToken = createAndSetAnonymousLoginToken();
        }
        else
        {
            loginToken = getLoginTokenService().findByValue(cookie.getValue());
            if(loginToken==null || !loginToken.isValid())
                loginToken = createAndSetAnonymousLoginToken();
        }
        return  loginToken;
    }

    protected final boolean isAnonymous()
    {
        LoginToken loginToken = getLoginToken();
        return loginToken.getType() == LoginToken.Type.ANONYMOUS;
    }

    protected final boolean isLoggedIn()
    {
        return !isAnonymous();
    }

    protected final User getLoggedInUser()
    {
        if(isAnonymous())
            return null;
        return getLoginToken().getUser();
    }
}
