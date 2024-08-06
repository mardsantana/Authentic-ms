package br.comautenticjwt.Authentic_ms.app.logout;

import br.comautenticjwt.Authentic_ms.app.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getName() != null) {
            // Marcar o usuário como inativo
            userService.setUserInactive(authentication.getName());
        }

        // Define o status da resposta como 200 OK
        response.setStatus(HttpServletResponse.SC_OK);

        // Envia uma mensagem simples no corpo da resposta
        response.getWriter().write("Logout successful");
        response.getWriter().flush();
    }
}