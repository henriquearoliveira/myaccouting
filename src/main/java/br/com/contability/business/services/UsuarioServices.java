package br.com.contability.business.services;

import br.com.contability.business.dto.CadastroDTO;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.UsuarioRepository;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;
import br.com.contability.exceptions.ObjetoNaoAutorizadoException;
import br.com.contability.utilitario.Util;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

@Service
public class UsuarioServices extends ServicesAbstract<Usuario, UsuarioRepository> {

    /**
     * @param email
     * @return
     */
    public Usuario getPelo(String email, String paginaRedirecionamento) {

        final Optional<Usuario> usuario = super.getJpa().getByEmail(email);

        return usuario.orElseThrow(() -> new ObjetoInexistenteExceptionMessage(paginaRedirecionamento,
                "Usuario inexistente para o email"));
    }

    /**
     * @param cadastro
     */
    public Usuario insere(CadastroDTO cadastro) {

        final Usuario usuario = new Usuario();
        usuario.getUsuarioByCadastro(cadastro, true);

        return super.insere(usuario, null);
    }

    /**
     * @param codigo
     * @return
     */
    public Usuario getUsuarioPelo(String codigo) {

        final Optional<Usuario> usuario = super.getJpa().getByCodigo(codigo);

        return usuario.orElseThrow(() -> new ObjetoInexistenteExceptionMessage("/login",
                "Codigo não encontrado por favor requisite outro codigo"));

    }

    /**
     * @param usuario
     */
    public void preenche(Usuario usuario) {

        final String emailAutenticado = Util.getEmailPelaAutenticacao();

        if (StringUtils.isEmpty(emailAutenticado)) {
            throw new ObjetoNaoAutorizadoException();
        }

        final Usuario usuarioAutenticado = this.getPelo(emailAutenticado, "/login");

        usuario.setId(usuarioAutenticado.getId());
        usuario.setSenha(usuarioAutenticado.getSenha());
        usuario.setEmail(usuarioAutenticado.getEmail());
        usuario.setSenhaMaster(
                usuarioAutenticado.getSenhaMaster() == null ? null : usuarioAutenticado.getSenhaMaster());
        usuario.setAtivo(usuarioAutenticado.isAtivo());
        usuario.setAdministrador(usuarioAutenticado.isAdministrador());
        usuario.setUploadImage(usuarioAutenticado.getUploadImage() == null ? null : usuarioAutenticado.getUploadImage());
    }
}
