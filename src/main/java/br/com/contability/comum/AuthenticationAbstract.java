package br.com.contability.comum;

import br.com.contability.business.Usuario;
import br.com.contability.business.services.UsuarioServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAbstract {

    private final UsuarioServices usuarioServices;

    public AuthenticationAbstract(UsuarioServices usuarioServices) {
        this.usuarioServices = usuarioServices;
    }

    /**
     * @return nameAuthentication
     */
    public Usuario getAutenticacao() {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        final String email = auth.getName();

        if (email.equals("anonymousUser"))
            return null;

        return usuarioServices.getPelo(email, "/login");
    }
}
