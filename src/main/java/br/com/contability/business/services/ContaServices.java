package br.com.contability.business.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.Conta;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.ContaRepository;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.exceptions.ObjetoInexistenteException;

@Service
public class ContaServices extends ServicesAbstract<Conta, ContaRepository> {

	/**
	 * @param usuario
	 * @param conta
	 */
	public void gravaConta(Usuario usuario, Conta conta) {

		conta.setUsuario(usuario);

		if (conta.getId() == null) {
			super.insere(conta);
		} else {
			super.atualiza(conta);
		}

	}

	/**
	 * @param usuario
	 * @return
	 */
	public List<Conta> seleciona(Usuario usuario) {

		List<Conta> contas = super.getJpa().selecionaPelo(usuario.getId());

		return contas;
	}

	/**
	 * @param usuario
	 * @param mv
	 * @param id
	 * @param model
	 * @return
	 */
	public ModelAndView getConta(Usuario usuario, ModelAndView mv, Long id, Model model) {

		if (id == 0 || id == null) {
			model.addAttribute("erro", "Identificador incorreto");
		}

		Conta conta = null;

		conta = super.getJpa().getConta(usuario.getId(), id);

		if (conta == null) {
			model.addAttribute("erro", "Impossível encontrar a conta desejada.");
			return mv;
		}

		mv.addObject("conta", conta);

		return mv;

	}

	public void removeConta(Usuario usuario, Long id) {

		if (id == null || confirmaVinculo(usuario, id))
			throw new ObjetoInexistenteException();

		super.remove(id);

	}

	private boolean confirmaVinculo(Usuario usuario, Long id) {

		Conta conta = super.getJpa().getConta(usuario.getId(), id);

		return conta == null;
	}

	public Conta getPeloLancamento(Long idLancamento) {
		return super.getJpa().getPeloLancamento(idLancamento);
	}

}
