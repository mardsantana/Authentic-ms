package br.comautenticjwt.Authentic_ms.app.api;


import br.comautenticjwt.Authentic_ms.app.model.Usuario;
import br.comautenticjwt.Authentic_ms.app.service.UserService;
import br.comautenticjwt.Authentic_ms.config.SecurityConfig;
import br.comautenticjwt.Authentic_ms.vo.UsuarioRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityConfig securityConfig;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticateUser(@RequestBody UsuarioRequest usuarioRequest) {
        log.info("[start] authentication user");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioRequest.getEmail(),
                            usuarioRequest.getSenha()
                    )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Usuario usuario = (Usuario) userService.findByEmail(userDetails.getUsername()).orElseThrow();
            if (!usuario.isAtivo()) {
                usuario.setAtivo(true);
                userService.updateUsuario(usuario);
            }
            String token = securityConfig.generateToken(userDetails.getUsername());
            TokenResponse tokenResponse = new TokenResponse(token); // Cria o objeto TokenResponse com o token
            return ResponseEntity.ok(tokenResponse); // Retorna o TokenResponse em formato JSON
        } catch (AuthenticationException e) {
            log.error("Invalid credentials", e);
            log.info("[finish] authentication user");
            return ResponseEntity.status(401).body(null); // Modificado para retornar null em caso de erro
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        log.info("[start] Creat user");
        try {
            // Assuming UserService has a method to register a new user
            userService.registerNewUser(usuarioRequest);
            log.info("[start] Success creating user");
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            log.error("Failed to register user", e);
            log.info("[finish] Creat user");
            return ResponseEntity.status(500).body("Failed to register user");
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        log.error("An error occurred", e);
        return ResponseEntity.status(500).body("An internal error occurred");
    }
}