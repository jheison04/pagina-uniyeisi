package com.uniyeisi.backend.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.uniyeisi.backend.entity.Usuario;
import com.uniyeisi.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {

    usuario.setPassword(
        passwordEncoder.encode(usuario.getPassword())
    );

    return usuarioRepository.save(usuario);
}

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }
}
