package br.com.contability.business.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.Lancamento;
import br.com.contability.business.TipoDeCategoria;
import br.com.contability.business.Usuario;
import br.com.contability.business.facade.SaldoFacade;
import br.com.contability.business.repository.LancamentoRepository;
import br.com.contability.comum.IServices;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.exceptions.ObjetoInexistenteException;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;
import br.com.contability.utilitario.CaixaDeFerramentas;

@Service
public class LancamentoServices extends ServicesAbstract<Lancamento, LancamentoRepository>
		implements IServices<Lancamento> {

	@Autowired
	private ContaServices contaServices;

	@Autowired
	private CategoriaServices categoriaServices;

	@Autowired
	private SaldoFacade saldoFacade;
	
	@Autowired
	private TrataParametrosServices parametroServices;

	/**
	 * @param lancamento
	 * @param usuario
	 */
	public void grava(Lancamento lancamento, Usuario usuario) {

		lancamento.setUsuario(usuario);
		BigDecimal bigDecimal = CaixaDeFerramentas.converteStringToBidDecimal(lancamento.getValorConversao());
		lancamento.setValorLancamento(bigDecimal);
		
		if (lancamento.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA && lancamento.isParcelado()) {
			
			insereLancamentoParcelado(lancamento, usuario);
			
		} else {

			lancamento.setParcelado(false);
			lancamento.setParcelas(null);
			lancamento.setDataHoraVencimento(null);
			insereLancamento(usuario, lancamento);
			
		}

	}

	/**
	 * @param lancamento
	 * @param usuario
	 */
	private void insereLancamentoParcelado(Lancamento lancamento, Usuario usuario) {
		
		int quantidadeParcelas = lancamento.getParcelas();
		
		defineValorParcelado(lancamento, quantidadeParcelas);
		
		for (int i = 0; i < quantidadeParcelas; i++) {
			
			Lancamento lancamentoParcela = clonaLancamento(lancamento);
			
//			LocalDate dataLancamento = lancamentoParcela.getDataHoraLancamento();
			LocalDate dataVencimento = lancamentoParcela.getDataHoraVencimento();
			
//			LocalDate dataLancamentoPlus = dataLancamento.plusMonths(i);
			LocalDate dataVencimentoPlus = dataVencimento.plusMonths(i);
			
			lancamentoParcela.setParcelas(i);
			lancamentoParcela.setDataHoraLancamento(dataVencimentoPlus);
			lancamentoParcela.setDataHoraVencimento(dataVencimentoPlus);
			
			insereLancamento(usuario, lancamentoParcela);
			
		}
	}

	private void defineValorParcelado(Lancamento lancamento, int quantidadeParcelas) {
		
		BigDecimal valorParcelado = lancamento.getValorLancamento()
				.divide(new BigDecimal(new String(""+quantidadeParcelas)), 2, BigDecimal.ROUND_HALF_UP);
		
		lancamento.setValorLancamento(valorParcelado);
		
	}

	private void insereLancamento(Usuario usuario, Lancamento lancamento) {
		
		if (super.insere(lancamento, null) != null)
			saldoFacade.atualizaSaldoUsuario(usuario, lancamento);
		
	}

	private Lancamento clonaLancamento(Lancamento lancamento) {
		
		Lancamento lancamentoClone = new Lancamento();
		lancamentoClone.setUsuario(lancamento.getUsuario());
		lancamentoClone.setCategoria(lancamento.getCategoria() == null ? null : lancamento.getCategoria());
		lancamentoClone.setConta(lancamento.getConta() == null ? null : lancamento.getConta());
		lancamentoClone.setDescricao(lancamento.getDescricao());
		
		lancamentoClone.setDataHoraCadastro(lancamento.getDataHoraCadastro() == null ? null : lancamento.getDataHoraCadastro());
		lancamentoClone.setDataHoraAtualizacao(lancamento.getDataHoraAtualizacao() == null ? null : lancamento.getDataHoraAtualizacao());
		lancamentoClone.setDataHoraVencimento(lancamento.getDataHoraVencimento() == null ? null : lancamento.getDataHoraVencimento());
		lancamentoClone.setDataHoraLancamento(lancamento.getDataHoraLancamento() == null ? null : lancamento.getDataHoraLancamento());
		
		lancamentoClone.setValorLancamento(lancamento.getValorLancamento());
		lancamentoClone.setPago(lancamento.isPago());
		lancamentoClone.setParcelado(lancamento.isParcelado());
		
		return lancamentoClone;
	}
	
	
	/**
	 * @param usuario
	 * @param calendar
	 * @return
	 */
	public List<Lancamento> seleciona(Usuario usuario, LocalDate localDate) {
		return super.getJpa().selecionaLancamentos(usuario.getId(), localDate.getMonthValue(), localDate.getYear());
	}
	
	/**
	 * @param usuario
	 * @param localDate
	 * @return
	 */
	public List<Lancamento> selecionaVencidos(Usuario usuario, LocalDate localDate) {
		return super.getJpa().selecionaVencidos(usuario.getId(), localDate.getMonthValue());
	}

	/**
	 * @param usuario
	 * @param dataHoraLancamento
	 * @return saldo
	 */
	public BigDecimal getSaldo(Usuario usuario, LocalDate localDate) {
		return super.getJpa().getSaldo(usuario.getId(), localDate.getMonthValue(), localDate.getYear());
	}
	
	/**
	 * @param usuario
	 * @param localDate
	 * @return
	 */
	public BigDecimal getSaldoProvavel(Usuario usuario, LocalDate localDate) {
		return super.getJpa().getSaldoProvavel(usuario.getId(), localDate.getMonthValue(), localDate.getYear());
	}

	/*
	 * FAZENDO COM STREAM (PARTICULAMENTE ACHO MUITA COISA KKKKK) FUNCIONA
	 * PERFEITAMENTE // TESTE SALDO Usuario usuario = auth.getAutenticacao();
	 * List<Lancamento> lancamentos = lancamentoServices.getJpa()
	 * .selecionaLancamentosPuroStream(usuario.getId(),
	 * calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)); Double saldo =
	 * lancamentos.stream().mapToDouble(f -> f.getCategoria().getTipoDeCategoria()
	 * == TipoDeCategoria.RECEITA ? f.getValorLancamento().doubleValue() :
	 * (-f.getValorLancamento().doubleValue())).sum();
	 * 
	 * System.out.println(saldo);
	 */

	/**
	 * @param usuario
	 * @param mv
	 * @param idLancamento
	 * @param model
	 * @return mv
	 */
	public ModelAndView getLancamento(Usuario usuario, ModelAndView mv, Object id) {
		
		Long idLancamento = parametroServices.trataParametroLong(id, "/lancamento");
		
		Optional<Lancamento> lancamento = super.getJpa().getLancamento(usuario.getId(), idLancamento);

		lancamento.orElseThrow(() -> new ObjetoInexistenteExceptionMessage("/lancamento", "Lançamento não encontrado"));

		lancamento.get().setValorConversao(lancamento.get().getValorLancamento().toString());

		mv.addObject("lancamento", lancamento.get());
		mv.addObject("categorias", categoriaServices.getPeloLancamento(lancamento.get().getId()));
		mv.addObject("contas", contaServices.getPeloLancamento(lancamento.get().getId()));

		return mv;

	}

	/**
	 * @param usuario
	 * @param id
	 */
	public void remove(Usuario usuario, Long lancamentoId) {

		if (lancamentoId == null || confirmaVinculo(usuario, lancamentoId))
			throw new ObjetoInexistenteException("Impossível encontrar o lançamento selecionado");

		Lancamento lancamento = super.get(lancamentoId, null);

		super.remove(lancamentoId, null);
		saldoFacade.atualizaSaldoUsuario(usuario, lancamento);

	}

	private boolean confirmaVinculo(Usuario usuario, Long lancamentoId) {

		Optional<Lancamento> lancamento = super.getJpa().getLancamento(usuario.getId(), lancamentoId);

		return !lancamento.isPresent();
	}

	public List<Lancamento> selecionaLancamentosAnoAtual(Usuario usuario) {

		return super.getJpa().selecionaLancamentosAnoAtual(usuario.getId());
		
	}

}
