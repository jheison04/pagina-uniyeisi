package com.uniyeisi.backend.service;

import com.uniyeisi.backend.dto.RegistroRequest;
import com.uniyeisi.backend.dto.UsuarioResponse;
import com.uniyeisi.backend.entity.Role;
import com.uniyeisi.backend.entity.Usuario;
import com.uniyeisi.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private RegistroRequest registroRequest;

    @BeforeEach
    void setUp() {
        registroRequest = new RegistroRequest(
                "Carlos",
                "Gomez",
                "carlos@uniyeisi.com",
                "ClaveSegura123",
                "3001234567",
                "Calle 10 # 20-30"
        );
    }

    @Test
    void testRegistrarUsuarioAsignaRolClienteObligatoriamente() {
        when(usuarioRepository.findByCorreo("carlos@uniyeisi.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("ClaveSegura123")).thenReturn("hashCifrado123");

        Usuario usuarioGuardado = new Usuario(
                1L,
                "Carlos",
                "Gomez",
                "carlos@uniyeisi.com",
                "hashCifrado123",
                "3001234567",
                "Calle 10 # 20-30",
                Role.CLIENTE
        );
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        UsuarioResponse respuesta = usuarioService.registrarUsuario(registroRequest);

        assertNotNull(respuesta);
        assertEquals(Role.CLIENTE, respuesta.getRol());
        assertEquals("carlos@uniyeisi.com", respuesta.getCorreo());
        assertEquals("Carlos", respuesta.getNombre());

        verify(usuarioRepository, times(1)).save(argThat(u -> u.getRol() == Role.CLIENTE));
    }

    @Test
    void testRegistrarUsuarioRechazaCorreoDuplicado() {
        when(usuarioRepository.findByCorreo("carlos@uniyeisi.com"))
                .thenReturn(Optional.of(new Usuario()));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.registrarUsuario(registroRequest)
        );

        assertEquals("El correo ya se encuentra registrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
