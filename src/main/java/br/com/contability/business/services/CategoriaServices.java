package br.com.contability.business.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.Categoria;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.CategoriaRepository;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.exceptions.ObjetoInexistenteException;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;

@Service
public class CategoriaServices extends ServicesAbstract<Categoria, CategoriaRepository> {
	
	/**
	 * @param model
	 * @param id
	 * @param mv
	 * @param usuario
	 * @return
	 */
	public ModelAndView getCategoria(Model model, Optional<Long> id, ModelAndView mv, Usuario usuario) {
		
		id.orElseThrow(() -> new ObjetoInexistenteExceptionMessage("/categoria", "Código inválido"));
		
		Optional<Categoria> categoria = null;
		
		categoria = super.getJpa().getCategorias(id.get(), usuario.getId());

		categoria.orElseThrow(() -> new ObjetoInexistenteExceptionMessage("/categoria", "Categoria não encontrada"));
		
		mv.addObject("categoria", categoria.get());
		mv.addObject("tipoDeCategorias", categoria.get().getTipoDeCategoria());

		return mv;
	}

	/**
	 * @param categoria
	 * @param usuario
	 */
	public void gravarCategoria(Categoria categoria, Usuario usuario) {

		categoria.setUsuario(usuario);
		
		if (categoria.getId() == null) {
			super.insere(categoria, null);
		} else {
			super.atualiza(categoria, null);
		}
	}

	/**
	 * @param usuario
	 * @return
	 */
	public List<Categoria> seleciona(Usuario usuario) {

		List<Categoria> categorias = super.getJpa().seleciona(usuario.getId());

		return categorias;
	}

	/*public ModelAndView re/move(Long id, Model model) {

		ModelAndView mv = new ModelAndView("/categoria/Categoria");

		if (id == null || id == 0) 
			return valorIncorreto.defineRedirecionamentoComMensagem("O obrigado não pode ser vazio", null, StringCaminhoPaginas.CATEGORIA);

		Categoria categoria = super.get(id);

		if (categoria == null)
			return valorIncorreto.defineRedirecionamentoComMensagem("O objeto não existe", null, StringCaminhoPaginas.CATEGORIA);

		super.remove(categoria.getId());

		return mv;

	}*/

	/**
	 * @param id
	 */
	public void removeCategoria(Long id) {

		if (id == null) {
			throw new ObjetoInexistenteException();
		}

		super.remove(id, null);

	}

	/**
	 * @param idLancamento
	 * @return Categoria
	 */
	public Categoria getPeloLancamento(Long idLancamento) {
		return super.getJpa().getPeloLancamento(idLancamento);
	}

}
