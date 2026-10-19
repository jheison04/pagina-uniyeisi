package com.uniyeisi.backend.service;

import com.uniyeisi.backend.dto.RegistroRequest;
import com.uniyeisi.backend.dto.UsuarioResponse;
import com.uniyeisi.backend.entity.Role;
import com.uniyeisi.backend.entity.Usuario;
import com.uniyeisi.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioResponse> obtenerUsuariosDTO() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponse registrarUsuario(RegistroRequest request) {
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        // Siempre asignar rol CLIENTE al registrarse públicamente
        usuario.setRol(Role.CLIENTE);

        Usuario guardado = usuarioRepository.save(usuario);
        return mapearAResponse(guardado);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuario.getRol() == null) {
            usuario.setRol(Role.CLIENTE);
        }

        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    public UsuarioResponse mapearAResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getRol()
        );
    }
}
