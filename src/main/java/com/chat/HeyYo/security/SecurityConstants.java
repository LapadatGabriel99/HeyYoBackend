package com.chat.HeyYo.security;

public class SecurityConstants {

    private SecurityConstants() {

    }

    public static final String SECRET = "secretsecuresecretsecuresecretsecuresecretsecuresecretsecuresecretsecuresecretsecuresecretsecuresecretsecuresecretsecuresecretsecuresecretsecure";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String REGISTER_URL = "/api/v*/user/register";
    public static final String LOGIN_URL = "/api/v*/user/login";
    public static final String MAIL_CONFIRMATION_URL = "/api/v*/user/confirm*";
    public static final String FORGOT_PASSWORD_URL = "/api/v*/user/forgot-password";
    public static final String RESET_PASSWORD_URL = "/api/v*/user/reset-password*";
    public static final String UPDATE_PASSWORD_URL = "/api/v*/user/update-password";
}
