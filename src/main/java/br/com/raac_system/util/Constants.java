package br.com.raac_system.util;

public final class Constants {

    private Constants() {
        // Prevent instantiation
    }

    public static final String TENANT_HEADER = "X-Tenant-ID";
    public static final String CACHE_KEY_TENANT_PREFIX = "tenant:";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MEDICO = "MEDICO";
    public static final String ROLE_ATENDENTE = "ATENDENTE";
    public static final String JWT_AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_BEARER_PREFIX = "Bearer ";

}
