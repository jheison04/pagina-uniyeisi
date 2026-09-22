package com.uniyeisi.backend.controller;

import com.uniyeisi.backend.dto.LoginResponse;
import com.uniyeisi.backend.entity.Usuario;
import com.uniyeisi.backend.security.JwtService;
import com.uniyeisi.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody Usuario usuario) {

        Usuario usuarioEncontrado =
                usuarioService.buscarPorCorreo(usuario.getCorreo());

        if (usuarioEncontrado == null) {
            return null;
        }

        boolean contraseñaCorrecta = passwordEncoder.matches(
                usuario.getPassword(),
                usuarioEncontrado.getPassword()
        );

        if (!contraseñaCorrecta) {
            return null;
        }

        String token = jwtService.generarToken(usuarioEncontrado);

        return new LoginResponse(
                token,
                usuarioEncontrado.getNombre(),
                usuarioEncontrado.getRol().name()
        );
    }
}