package br.com.contability.utilitario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {

    private Util() {
    }

    public static String getEmailPelaAutenticacao() {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        final String email = auth.getName();

        if (email.equals("anonymousUser"))
            return null;

        return email;
    }
}
