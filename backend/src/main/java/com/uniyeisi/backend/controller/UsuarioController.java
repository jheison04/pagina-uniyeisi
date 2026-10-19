package com.uniyeisi.backend.controller;

import com.uniyeisi.backend.dto.LoginRequest;
import com.uniyeisi.backend.dto.LoginResponse;
import com.uniyeisi.backend.dto.RegistroRequest;
import com.uniyeisi.backend.dto.UsuarioResponse;
import com.uniyeisi.backend.entity.Usuario;
import com.uniyeisi.backend.security.JwtService;
import com.uniyeisi.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UsuarioResponse>> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerUsuariosDTO());
    }

    @PostMapping
    public ResponseEntity<?> guardarUsuario(@RequestBody RegistroRequest request) {
        try {
            UsuarioResponse nuevoUsuario = usuarioService.registrarUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        if (loginRequest == null || loginRequest.getCorreo() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Correo y contraseña son requeridos");
        }

        Usuario usuarioEncontrado =
                usuarioService.buscarPorCorreo(loginRequest.getCorreo());

        if (usuarioEncontrado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }

        boolean contraseñaCorrecta = passwordEncoder.matches(
                loginRequest.getPassword(),
                usuarioEncontrado.getPassword()
        );

        if (!contraseñaCorrecta) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }

        String token = jwtService.generarToken(usuarioEncontrado);

        return ResponseEntity.ok(new LoginResponse(
                token,
                usuarioEncontrado.getNombre(),
                usuarioEncontrado.getRol() != null ? usuarioEncontrado.getRol().name() : "CLIENTE"
        ));
    }
}
