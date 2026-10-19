package com.uniyeisi.backend.security;

import com.uniyeisi.backend.entity.Role;
import com.uniyeisi.backend.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    // Clave de prueba de al menos 256 bits para HMAC-SHA256 en entorno de pruebas unitarias
    private final String testSecret = "ClaveSecretaDePruebaParaTestUnitarioConAlMenos32CaracteresLongitud!";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", testSecret);
    }

    @Test
    void testGenerarYValidarToken() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("estudiante@uniyeisi.com");
        usuario.setRol(Role.CLIENTE);

        String token = jwtService.generarToken(usuario);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String username = jwtService.extractUsername(token);
        assertEquals("estudiante@uniyeisi.com", username);

        String rol = jwtService.extractRole(token);
        assertEquals("CLIENTE", rol);

        assertFalse(jwtService.isTokenExpired(token));

        UserDetails userDetails = new User(
                "estudiante@uniyeisi.com",
                "dummyPassword",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );

        assertTrue(jwtService.isTokenValid(token, userDetails));
        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void testTokenInvalidoConUsuarioDiferente() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("estudiante@uniyeisi.com");
        usuario.setRol(Role.CLIENTE);

        String token = jwtService.generarToken(usuario);

        UserDetails otroUsuario = new User(
                "otro@uniyeisi.com",
                "dummyPassword",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );

        assertFalse(jwtService.isTokenValid(token, otroUsuario));
    }

    @Test
    void testTokenAlteradoEsInvalido() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("admin@uniyeisi.com");
        usuario.setRol(Role.ADMIN);

        String token = jwtService.generarToken(usuario);
        String tokenAlterado = token.substring(0, token.length() - 5) + "abcde";

        assertFalse(jwtService.isTokenValid(tokenAlterado));
    }

    @Test
    void testTokenExpiradoEsInvalido() {
        javax.crypto.SecretKey key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(
                testSecret.getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );

        String tokenExpirado = io.jsonwebtoken.Jwts.builder()
                .subject("expirado@uniyeisi.com")
                .claim("rol", "CLIENTE")
                .issuedAt(new java.util.Date(System.currentTimeMillis() - 1000L * 60 * 120))
                .expiration(new java.util.Date(System.currentTimeMillis() - 1000L * 60 * 60))
                .signWith(key)
                .compact();

        UserDetails userDetails = new User(
                "expirado@uniyeisi.com",
                "dummyPassword",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );

        assertTrue(jwtService.isTokenExpired(tokenExpirado));
        assertFalse(jwtService.isTokenValid(tokenExpirado));
        assertFalse(jwtService.isTokenValid(tokenExpirado, userDetails));
    }
}
