package br.comautenticjwt.Authentic_ms.app.serviceimpl;

import br.comautenticjwt.Authentic_ms.app.model.Usuario;
import br.comautenticjwt.Authentic_ms.app.repository.UsuarioRepository;
import br.comautenticjwt.Authentic_ms.app.service.UserService;
import br.comautenticjwt.Authentic_ms.vo.UsuarioRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void registerNewUser(UsuarioRequest usuarioRequest) {
        log.info("[start] UserServiceImpl - registerNewUser");
        // Cria um novo objeto Usuario com base nos dados do UsuarioRequest
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(usuarioRequest.getEmail());
        novoUsuario.setNome(usuarioRequest.getNome()); // Define o nome
        novoUsuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha())); // Criptografa a senha
        log.info("[finish] UserServiceImpl - registerNewUser");
        // Salva o novo usuário no banco de dados
        usuarioRepository.save(novoUsuario);
    }
    @Override
    public void updateUsuario(Usuario usuario) {
        log.info("[start] UserServiceImpl - updateUsuario");
        usuarioRepository.save(usuario);
        log.info("[finish] UserServiceImpl - updateUsuario");
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        log.info("[start] UserServiceImpl - findByEmail");
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public void setUserInactive(String email) {
        log.info("[start] UserServiceImpl - setUserInactive");
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
        log.info("[finish] UserServiceImpl - setUserInactive");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("[start] UserServiceImpl - loadUserByUsername");
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        log.info("[finish] UserServiceImpl - loadUserByUsername");

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities("USER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!usuario.isAtivo())
                .build();
    }

}
