package br.com.contability.business.services;

import br.com.contability.business.dto.CadastroDTO;
import br.com.contability.business.Usuario;
import br.com.contability.utilitario.CaixaDeFerramentas;
import br.com.contability.utilitario.email.EmailParameters;
import br.com.contability.utilitario.email.IEnviaEmail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CadastrarServices {

    private final CodigoUsuarioServices services;
    private final IEnviaEmail enviaEmail;

    public CadastrarServices(CodigoUsuarioServices services, IEnviaEmail enviaEmail) {
        this.services = services;
        this.enviaEmail = enviaEmail;
    }

    /**
     * @param cadastro
     * @param usuario
     */
    public void confirmaUsuario(CadastroDTO cadastro, Usuario usuario, HttpServletRequest request) {

        final String codigo = CaixaDeFerramentas.geraCodigo(20);
        services.insereCodigoUsuario(usuario, codigo);

        final EmailParameters email = EmailParameters.builder()
                .assunto("Ativação de cadastro no My Accounting")
                .para(cadastro.getEmail())
                .codigo(codigo)
                .url(CaixaDeFerramentas.configureURLdesired(request, "/login/requestcode?codigo="))
                .templateEmail("mail/CadastroUsuario")
                .build();

        enviaEmail.envia(email);
    }

}
