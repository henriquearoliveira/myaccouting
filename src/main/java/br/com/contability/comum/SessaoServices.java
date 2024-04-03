package br.com.contability.comum;

import br.com.contability.business.Lancamento;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.LancamentoServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SessaoServices {

    private final AuthenticationAbstract auth;
    private final LancamentoServices lancamentoServices;

    public SessaoServices(AuthenticationAbstract auth, LancamentoServices lancamentoServices) {
        this.auth = auth;
        this.lancamentoServices = lancamentoServices;
    }

    /**
     * @param session
     */
    public void configuraSessionComSessao(HttpSession session) {

        final Usuario usuario = auth.getAutenticacao();
        this.configuraSessao(usuario, session);
    }

    /**
     * @param session
     */
    public void configuraSessionComUsuario(Usuario usuario, HttpSession session) {
        this.configuraSessao(usuario, session);
    }

    private void configuraSessao(Usuario usuario, HttpSession session) {
        session.setAttribute("userEmail", usuario.getEmail());
        session.setAttribute("userUrl", usuario.getUploadImage() == null ? null
                : usuario.getUploadImage().getSecureUrl());
        session.setAttribute("userDate", usuario.getDataHoraCadastro());
        session.setAttribute("lancamentosVencidos",
                lancamentoServices.selecionaVencidosAnteriorA(usuario, LocalDate.now(), null));
    }

    public void atualizaVencidos(HttpSession session, List<Lancamento> lancamentosVencidos) {
        session.setAttribute("lancamentosVencidos", lancamentosVencidos);
    }
}
