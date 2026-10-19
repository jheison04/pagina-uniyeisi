package com.uniyeisi.backend.security;

import com.uniyeisi.backend.entity.Usuario;
import com.uniyeisi.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));

        String rolNombre = usuario.getRol() != null ? usuario.getRol().name() : "CLIENTE";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rolNombre);

        return new User(
                usuario.getCorreo(),
                usuario.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
