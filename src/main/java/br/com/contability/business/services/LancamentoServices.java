package br.com.contability.business.services;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.Lancamento;
import br.com.contability.business.Lancamentos;
import br.com.contability.business.TipoDeCategoria;
import br.com.contability.business.Usuario;
import br.com.contability.business.facade.SaldoFacade;
import br.com.contability.business.repository.LancamentoRepository;
import br.com.contability.comum.IServices;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.comum.SessaoServices;
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
	private ConfiguraArquivosServices arquivosServices;

	private LeitorPlanilhaStrategy<Lancamento, LancamentoServices> leitorPlanilhaExcel = new 
			LeitorPlanilhasExcel<>(this);

	private LeitorPlanilhaStrategy<Lancamento, LancamentoServices> leitorPlanilhaLibreOffice = new
			LeitorPlanilhasLibreOffice<>(this);

	@Autowired
	private SaldoFacade saldoFacade;

	@Autowired
	private SessaoServices sessionServices;

	@Autowired
	private TrataParametrosServices parametroServices;

	/**
	 * @param lancamento
	 * @param usuario
	 */
	public void grava(Lancamento lancamento, Usuario usuario, HttpSession session) {

		lancamento.setUsuario(usuario);

		setaValorLancamento(lancamento);

		if (lancamento.getId() == null) {
			gravaLancamento(lancamento);
		} else {
			atualizaLancamento(lancamento, session);
		}

	}

	private void atualizaLancamento(Lancamento lancamento, HttpSession session) {

		if (super.atualiza(lancamento, null) != null) {
			saldoFacade.atualizaSaldoUsuario(lancamento);

			if (lancamento.getDataHoraVencimento().isBefore(LocalDate.now())) {
				sessionServices.atualizaVencidos(session,
						this.selecionaVencidosAnteriorA(lancamento.getUsuario(), LocalDate.now()));
			}
		}

	}

	private void gravaLancamento(Lancamento lancamento) {

		if (lancamento.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA && lancamento.isParcelado()) {

			insereLancamentoParcelado(lancamento);

		} else {

			lancamento.setParcelado(false);
			lancamento.setParcelas(null);
			lancamento.setDataHoraVencimento(null);
			insereLancamento(lancamento);

		}

	}

	private void setaValorLancamento(Lancamento lancamento) {

		BigDecimal bigDecimal = CaixaDeFerramentas.converteStringToBidDecimal(lancamento.getValorConversao());

		lancamento.setValorLancamento(bigDecimal);

	}

	/**
	 * @param lancamento
	 * @param usuario
	 */
	private void insereLancamentoParcelado(Lancamento lancamento) {

		int quantidadeParcelas = lancamento.getParcelas();

		defineValorParcelado(lancamento, quantidadeParcelas);

		for (int i = 0; i < quantidadeParcelas; i++) {

			Lancamento lancamentoParcela = clonaLancamento(lancamento);

			// LocalDate dataLancamento = lancamentoParcela.getDataHoraLancamento();
			LocalDate dataVencimento = lancamentoParcela.getDataHoraVencimento();

			// LocalDate dataLancamentoPlus = dataLancamento.plusMonths(i);
			LocalDate dataVencimentoPlus = dataVencimento.plusMonths(i);

			lancamentoParcela.setParcelas(i + 1);
			lancamentoParcela.setDataHoraLancamento(dataVencimentoPlus);
			lancamentoParcela.setDataHoraVencimento(dataVencimentoPlus);

			insereLancamento(lancamentoParcela);

		}
	}

	private void defineValorParcelado(Lancamento lancamento, int quantidadeParcelas) {

		BigDecimal valorParcelado = lancamento.getValorLancamento()
				.divide(new BigDecimal(new String("" + quantidadeParcelas)), 2, BigDecimal.ROUND_HALF_UP);

		lancamento.setValorLancamento(valorParcelado);

	}

	private void insereLancamento(Lancamento lancamento) {

		if (super.insere(lancamento, null) != null)
			saldoFacade.atualizaSaldoUsuario(lancamento);

	}

	private Lancamento clonaLancamento(Lancamento lancamento) {

		Lancamento lancamentoClone = new Lancamento();
		lancamentoClone.setUsuario(lancamento.getUsuario());
		lancamentoClone.setCategoria(lancamento.getCategoria() == null ? null : lancamento.getCategoria());
		lancamentoClone.setConta(lancamento.getConta() == null ? null : lancamento.getConta());
		lancamentoClone.setDescricao(lancamento.getDescricao());

		lancamentoClone.setDataHoraCadastro(
				lancamento.getDataHoraCadastro() == null ? null : lancamento.getDataHoraCadastro());
		lancamentoClone.setDataHoraAtualizacao(
				lancamento.getDataHoraAtualizacao() == null ? null : lancamento.getDataHoraAtualizacao());
		lancamentoClone.setDataHoraVencimento(
				lancamento.getDataHoraVencimento() == null ? null : lancamento.getDataHoraVencimento());
		lancamentoClone.setDataHoraLancamento(
				lancamento.getDataHoraLancamento() == null ? null : lancamento.getDataHoraLancamento());

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
	 * @param calendar
	 * @return
	 */
	public List<Lancamento> selecionaVencidosDa(Usuario usuario, LocalDate localDate) {
		return super.getJpa().selecionaVencidosDa(usuario.getId(), localDate);
	}

	/**
	 * @param usuario
	 * @param localDate
	 * @return
	 */
	public List<Lancamento> selecionaVencidosAnteriorA(Usuario usuario, LocalDate localDate) {
		return super.getJpa().selecionaVencidos(usuario.getId(), localDate);
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
		saldoFacade.atualizaSaldoUsuario(lancamento);

	}

	private boolean confirmaVinculo(Usuario usuario, Long lancamentoId) {

		Optional<Lancamento> lancamento = super.getJpa().getLancamento(usuario.getId(), lancamentoId);

		return !lancamento.isPresent();
	}

	public List<Lancamento> selecionaLancamentosAnoAtual(Usuario usuario) {

		return super.getJpa().selecionaLancamentosAnoAtual(usuario.getId());

	}

	/**
	 * @param multipartFile
	 * @return
	 */
	public List<Lancamento> configuraPlanilha(MultipartFile multipartFile) {

		File file = arquivosServices.configuraArquivo(multipartFile);

		String nomeArquivo = file.getName();

		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1, nomeArquivo.length());

		return extensao.equals("ods") ? leitorPlanilhaLibreOffice.configuraFileToObject(file)
				: leitorPlanilhaExcel.configuraFileToObject(file);

	}

	/**
	 * @param usuario
	 * @param lancamentos
	 */
	public void gravaImportacao(Usuario usuario, Lancamentos lancamentoImportacao) {

		List<Lancamento> lancamentos = lancamentoImportacao.getLancamentos();

		verificaInconsistencias(lancamentos);

		retiraSinaisNegativos(lancamentos);

		setaUsuarios(lancamentos, usuario);

		lancamentos.forEach(l -> System.out.println("valor: " + l.getValorLancamento()));

		lancamentos.forEach(l -> gravaLancamento(l));
	}

	/**
	 * @param lancamentos
	 * @param usuario
	 */
	private void setaUsuarios(List<Lancamento> lancamentos, Usuario usuario) {

		lancamentos.forEach(l -> l.setUsuario(usuario));

	}

	private void retiraSinaisNegativos(List<Lancamento> lancamentos) {

		lancamentos.forEach(l -> {

			BigDecimal valor = l.getValorLancamento();

			BigDecimal valorCorrigido = CaixaDeFerramentas.alteraValorParaPositivo(valor);
			l.setValorLancamento(valorCorrigido);

		});

	}

	/**
	 * @param lancamentos
	 */
	private void verificaInconsistencias(List<Lancamento> lancamentos) {

		lancamentos.forEach(l -> {

			verificaData(l);

			verificaDescricao(l);

			verificaCategoria(l);

			verificaValorLancamento(l);

		});

	}

	private void verificaData(Lancamento l) {
		if (l.getCategoria() == null)
			throw new ObjetoInexistenteExceptionMessage("/import", "Data não informada.");
	}

	private void verificaDescricao(Lancamento l) {
		if (l.getCategoria() == null)
			throw new ObjetoInexistenteExceptionMessage("/import", "Descrição não informada.");
	}

	private void verificaCategoria(Lancamento l) {
		if (l.getCategoria() == null)
			throw new ObjetoInexistenteExceptionMessage("/import", "Categoria não vinculada.");
	}

	private void verificaValorLancamento(Lancamento l) {
		if (l.getCategoria() == null)
			throw new ObjetoInexistenteExceptionMessage("/import", "Valor não informado.");
	}

	@Override
	public void preencheObjetoPlanilhaExcel(List<Lancamento> objetos, Iterator<Row> iteratorRow) {

		while (iteratorRow.hasNext()) {

			Row nextRow = (Row) iteratorRow.next();

			if (pulaLinhaSeNecessario(nextRow.getRowNum()))
				continue;

			Iterator<Cell> cellIterator = nextRow.iterator();

			Lancamento objeto = new Lancamento();

			while (cellIterator.hasNext()) {

				Cell nextCell = (Cell) cellIterator.next();

				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {

				case 0:

					Calendar date = DateUtil.getJavaCalendar((double) getCellValue(nextCell));
					objeto.setDataHoraLancamento(CaixaDeFerramentas.converteDateToLocalDate(date.getTime()));
					break;

				case 1:
					objeto.setDescricao((String) getCellValue(nextCell));
					break;

				case 2:

					BigDecimal valorLancamento = configuraValorLancamento(nextCell);
					objeto.setValorLancamento(valorLancamento);
					break;

				default:
					break;
				}
			}

			objetos.add(objeto);

		}
	}

	/**
	 * @param nextRow
	 * @return
	 */
	private boolean pulaLinhaSeNecessario(int nextRow) {
		return nextRow == 0;
	}

	/**
	 * @param cell
	 * @return
	 */
	public Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}

		return null;
	}

	/**
	 * @param nextCell
	 * @return
	 */
	private BigDecimal configuraValorLancamento(Cell nextCell) {

		Double valorExcel = (Double) getCellValue(nextCell);

		BigDecimal valorLancamento = BigDecimal.valueOf(valorExcel);
		return valorLancamento;
	}

	@Override
	public void preencheObjetoPlanilhaLibreOffice(List<Lancamento> objetos, Sheet sheet) {

		// Get row count and column count
		int nColCount = sheet.getColumnCount();
		int nRowCount = sheet.getRowCount();

		System.out.println("Rows :" + nRowCount);
		System.out.println("Cols :" + nColCount);
		// Iterating through each row of the selected sheet
		MutableCell cell = null;

		for (int nRowIndex = 0; nRowIndex < nRowCount; nRowIndex++) {

			if (pulaLinhaSeNecessario(nRowIndex))
				continue;

			Lancamento lancamento = new Lancamento();

			// Iterating through each column
			int nColIndex = 0;

			for (; nColIndex < nColCount; nColIndex++) {

				cell = sheet.getCellAt(nColIndex, nRowIndex);

				alimentaValor(nColIndex, lancamento, cell);

				System.out.print(cell.getValue() + " ");

			}
			System.out.println();
		}

	}

	/**
	 * @param nColIndex
	 * @param lancamento
	 */
	private void alimentaValor(int nColIndex, Lancamento lancamento, MutableCell cell) {

		switch (nColIndex) {

		case 0:

			lancamento.setDataHoraLancamento(
					CaixaDeFerramentas.calendarFromStringDiaMesAnoDate(cell.getValue().toString()));

			break;

		case 1:

			lancamento.setDescricao(cell.getValue().toString());

			break;

		case 2:

			lancamento.setValorLancamento(new BigDecimal(cell.getValue().toString()));

			break;

		default:
			break;
		}

	}

}
