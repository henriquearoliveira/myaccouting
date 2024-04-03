package br.com.contability.business.services;

import br.com.contability.business.Lancamento;
import br.com.contability.business.Mes;
import br.com.contability.business.TipoDeCategoria;
import br.com.contability.business.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Component
public class IndexServices {

    // VARIÁVEL RESPONSÁVEL PELA ECONOMIA DE PROCESSAMENTO DO BANCO
    private List<Lancamento> lancamentos = null;

    List<Integer> mesesLocalDateTime = null;

    private final ContaServices contaServices;
    private final LancamentoServices lancamentoServices;

    public IndexServices(ContaServices contaServices, LancamentoServices lancamentoServices) {
        this.contaServices = contaServices;
        this.lancamentoServices = lancamentoServices;
    }

    public List<String> getBalanceteMeses(Usuario usuario) {

        lancamentos = lancamentoServices.selecionaLancamentosAnoAtual(usuario);

        // SELECIONO APENAS OS MESES DO ANO QUE HÁ LANÇAMENTO SEM REPETIÇÕES
        mesesLocalDateTime = lancamentos
                .stream()
                .map(l -> l.getDataHoraCadastro().getMonth().getValue())
                .distinct()
                .toList();

        // BUSCO O MES REFERENTE
        return mesesLocalDateTime
                .stream()
                .map(Mes::getBy)
                .toList();
    }

    public List<Double> getBalanceteReceitas() {
        return mesesLocalDateTime
                .stream()
                .map(m -> {
                    final Stream<Lancamento> lan = lancamentos.stream().filter(l -> l.getDataHoraLancamento().getMonth().getValue() == m
                            && l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.RECEITA);

                    return lan.mapToDouble(l -> l.getValorLancamento().doubleValue()).sum();
                })
                .toList();
    }

    public List<Double> getBalanceteDespesas() {
        return mesesLocalDateTime
                .stream()
                .map(m -> {
                    final Stream<Lancamento> lan = lancamentos.stream().filter(l -> l.getDataHoraLancamento().getMonth().getValue() == m
                            && l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA);
                    return lan.mapToDouble(l -> l.getValorLancamento().doubleValue()).sum();
                })
                .toList();
    }

    public Double getReceitasMes() {
        return lancamentos
                .stream()
                .filter(l -> l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.RECEITA
                        && l.getDataHoraLancamento().getMonth() == LocalDate.now().getMonth())
                .mapToDouble(l -> l.getValorLancamento().doubleValue())
                .sum();
    }

    public Double getDespesasMes() {
        return lancamentos
                .stream()
                .filter(l -> l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA
                        && l.getDataHoraLancamento().getMonth() == LocalDate.now().getMonth())
                .mapToDouble(l -> l.getValorLancamento().doubleValue())
                .sum();
    }

    public Object getSaldoMes() {
        return lancamentos
                .stream()
                .filter(l -> l.getDataHoraLancamento().getMonth() == LocalDate.now().getMonth())
                .mapToDouble(l -> l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.RECEITA ?
                        l.getValorLancamento().doubleValue() : -l.getValorLancamento().doubleValue())
                .sum();
    }

    public Integer getContas(Usuario usuario) {
        return contaServices.selecionaNumeroContasDo(usuario);
    }
}
