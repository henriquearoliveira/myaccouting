package br.com.contability.business.services;

import br.com.contability.business.dto.AlteraSenhaDTO;
import br.com.contability.business.CodigoUsuario;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.CodigoUsuarioRepository;
import br.com.contability.comum.ServicesAbstract;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CodigoUsuarioServices extends ServicesAbstract<CodigoUsuario, CodigoUsuarioRepository> {

    private final UsuarioServices usuarioServices;

    public CodigoUsuarioServices(UsuarioServices usuarioServices) {
        this.usuarioServices = usuarioServices;
    }

    /**
     * @param usuario
     * @param codigo
     */
    public void insereCodigoUsuario(Usuario usuario, String codigo) {
        verificaCodigosAtivos(usuario);
        verificaCodigoJaExistente(usuario, codigo);

        final CodigoUsuario codigoUsuario = geraCodigoUsuario(codigo, usuario);
        super.insere(codigoUsuario, null);
    }

    /**
     * @param codigo
     * @param usuario
     * @return FACILITA A REUTILIZAÇÃO
     */
    public CodigoUsuario geraCodigoUsuario(String codigo, Usuario usuario) {
        final CodigoUsuario codigoUsuario = new CodigoUsuario();
        codigoUsuario.setCodigo(codigo);
        codigoUsuario.setUsuario(usuario);
        return codigoUsuario;
    }

    /**
     * @param usuario
     * @param codigo  SE O CÓDIGO JÁ EXISTIR NA TABELA, É GERADO OUTRO ATÉ A COERENCIA CORRETA.
     */
    private void verificaCodigoJaExistente(Usuario usuario, String codigo) {
        final Optional<CodigoUsuario> codigoUsuario = super.getJpa().verificaJaExistente(codigo);
        codigoUsuario.ifPresent(i -> insereCodigoUsuario(usuario, codigo));

    }

    /**
     * @param usuario VERIFICA SE O USUARIO NÃO ESTÁ COM O CÓDIGO ATIVO
     */
    private void verificaCodigosAtivos(Usuario usuario) {
        final Optional<CodigoUsuario> codigoUsuario = super.getJpa().verificaCodigoAtivo(usuario);
        codigoUsuario.ifPresent(i -> remove(i.getId(), null));
    }

    /**
     * @param codigo
     */
    public void confirmaCodigoUsuario(String codigo) {

        final Usuario usuario = usuarioServices.getUsuarioPelo(codigo);
        usuario.setAtivo(true);
        usuarioServices.atualiza(usuario, null);
        super.atualiza(setFalseCodigoUsuario(usuario, codigo), null);

    }

    /**
     * @param usuario
     * @param codigo
     * @return
     */
    private CodigoUsuario setFalseCodigoUsuario(Usuario usuario, String codigo) {

        final CodigoUsuario codigoUsuario = super.getJpa().get(usuario, codigo).orElseThrow(EntityNotFoundException::new);
        codigoUsuario.setAtivo(false);
        return codigoUsuario;
    }

    /**
     * @param alteraSenha
     */
    public void alteraSenha(AlteraSenhaDTO alteraSenha) {
        final Usuario usuario = usuarioServices.getPelo(alteraSenha.getEmail(), "/alterasenha?email=" + alteraSenha.getEmail()
                + "&codigo=" + alteraSenha.getCodigo());

        usuario.setSenha(new BCryptPasswordEncoder().encode(alteraSenha.getPassword()));

        usuarioServices.atualiza(usuario, null);
        setFalseCodigoUsuario(usuario, alteraSenha.getCodigo());
    }

}
