package br.comautenticjwt.Authentic_ms.app.service;

import br.comautenticjwt.Authentic_ms.app.model.Usuario;
import br.comautenticjwt.Authentic_ms.vo.UsuarioRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    void registerNewUser(UsuarioRequest usuarioRequest);
    void updateUsuario(Usuario usuario);
    Optional<Usuario> findByEmail(String username);
    void setUserInactive(String name);
    UserDetails loadUserByUsername(String username);
}
