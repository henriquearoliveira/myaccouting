package br.com.contability.business.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.Categoria;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.CategoriaRepository;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.exceptions.ObjetoInexistenteException;

@Service
public class CategoriaServices extends ServicesAbstract<Categoria, CategoriaRepository> {

	/**
	 * @param model
	 * @param id
	 * @param mv
	 * @param usuario
	 * @return
	 */
	public ModelAndView getCategoria(Model model, Long id, ModelAndView mv, Usuario usuario) {

		if (id == 0 || id == null) {
			model.addAttribute("erro", "Identificador incorreto");
		}

		Categoria categoria = null;

		categoria = super.getJpa().getCategorias(id, usuario.getId());

		if (categoria == null) {
			model.addAttribute("erro", "Impossível encontrar a categoria desejada");
			return mv;
		}

		mv.addObject("categoria", categoria);
		mv.addObject("tipoDeCategorias", categoria.getTipoDeCategoria());

		return mv;
	}

	/**
	 * @param categoria
	 * @param usuario
	 */
	public void gravarCategoria(Categoria categoria, Usuario usuario) {

		categoria.setUsuario(usuario);
		
		if (categoria.getId() == null) {
			super.insere(categoria);
		} else {
			super.atualiza(categoria);
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

	/**
	 * @param id
	 * @param model
	 */
	public ModelAndView remove(Long id, Model model) {

		ModelAndView mv = new ModelAndView("/categoria/Categoria");

		if (id == null || id == 0) {

			model.addAttribute("erro", "O objeto não pode ser vazio");
			return mv;

		}

		Categoria categoria = super.get(id);

		if (categoria == null) {
			model.addAttribute("erro", "O objeto não existe");
			return mv;
		}

		super.remove(categoria.getId());

		return mv;

	}

	/**
	 * @param id
	 */
	public void removeCategoria(Long id) {

		if (id == null) {
			throw new ObjetoInexistenteException();
		}

		super.remove(id);

	}

	/**
	 * @param idLancamento
	 * @return Categoria
	 */
	public Categoria getPeloLancamento(Long idLancamento) {
		return super.getJpa().getPeloLancamento(idLancamento);
	}

}
