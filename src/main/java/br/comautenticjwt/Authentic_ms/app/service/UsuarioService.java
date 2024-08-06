package br.comautenticjwt.Authentic_ms.app.service;

import br.comautenticjwt.Authentic_ms.app.repository.UsuarioRepository;
import br.comautenticjwt.Authentic_ms.vo.UsuarioRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private void validaEmail(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
    }
}
