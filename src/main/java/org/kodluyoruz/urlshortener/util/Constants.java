package org.kodluyoruz.urlshortener.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    private static BCryptPasswordEncoder encoder = null;

    //singleton design pattern
    //yani sinifin yalnizca 1 tane ornegini ala biliriz

    public static final String LOGIN_TOKEN_COOKIE_NAME = "login-token";
    public static final Long DAY_MILLIS = 24L * 60 *60 * 1000;
    public static final Long ANONYMOUS_LOGIN_TOKEN_EXPIRATION_DURATION = 7L * DAY_MILLIS;
    public static final Long LOGGED_IN_LOGIN_TOKEN_EXPIRATION_DURATION = 365L * DAY_MILLIS;
    public static BCryptPasswordEncoder getEncoder()
    {
        if (encoder == null) {
            encoder = new BCryptPasswordEncoder();
        }
        return encoder;
    }
}
