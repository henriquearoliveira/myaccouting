package br.com.contability.business.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contability.business.CodigoUsuario;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.CodigoUsuarioRepository;
import br.com.contability.comum.ServicesAbstract;

@Service
public class CodigoUsuarioServices extends ServicesAbstract<CodigoUsuario, CodigoUsuarioRepository> {
	
	@Autowired
	private UsuarioServices usuarioServices;

	/**
	 * @param usuario
	 * @param codigo
	 */
	public void insere(Usuario usuario, String codigo) {

		verificaCodigosAtivos(usuario);
		verificaCodigoJaExistente(usuario, codigo);

		CodigoUsuario codigoUsuario = new CodigoUsuario();
		codigoUsuario.setCodigo(codigo);
		codigoUsuario.setUsuario(usuario);

		super.insere(codigoUsuario, null);

	}

	/**
	 * @param usuario
	 * @param codigo
	 * 
	 * SE O CÓDIGO JÁ EXISTIR NA TABELA, É GERADO OUTRO ATÉ A COERENCIA CORRETA.
	 */
	private void verificaCodigoJaExistente(Usuario usuario, String codigo) {
		
		Optional<CodigoUsuario> codigoUsuario = super.getJpa().verificaJaExistente(codigo);
		
		codigoUsuario.ifPresent(i -> {
			insere(usuario, codigo);
		});
		
	}

	/**
	 * @param usuario
	 * @param codigo
	 * 
	 *            VERIFICA SE O USUARIO NÃO ESTÁ COM O CÓDIGO ATIVO
	 */
	private void verificaCodigosAtivos(Usuario usuario) {

		Optional<CodigoUsuario> codigoUsuario = super.getJpa().verificaCodigoAtivo(usuario);
		
		codigoUsuario.ifPresent(i -> {
			remove(i.getId(), null);
		});

	}

	/**
	 * @param codigo
	 */
	public void confirmaCodigoUsuario(String codigo) {
		
		Usuario usuario = usuarioServices.getUsuarioPelo(codigo);
		usuario.setAtivo(true);
		
		usuarioServices.atualiza(usuario, null);
		
		super.atualiza(setFalseCodigoUsuario(usuario, codigo), null);
		
	}
	
	/**
	 * @param usuario
	 * @param codigo
	 * @return
	 */
	private CodigoUsuario setFalseCodigoUsuario(Usuario usuario, String codigo){
		
		CodigoUsuario codigoUsuario = super.getJpa().get(usuario, codigo).get();
		codigoUsuario.setAtivo(false);
		
		return codigoUsuario;
		
	}

}
