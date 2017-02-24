package br.com.contability.business;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.contability.comum.BeanIdentificavel;

@Entity
@Table(name = "lancamento", indexes = { @Index(name = "index_lancamento", columnList = "id", unique = false)})
public class Lancamento extends BeanIdentificavel {
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private Categoria categoria;
	
	@JsonIgnore
	@ManyToOne(optional = true)
	private Conta conta;
	
	@Column(length = 100, nullable = false)
	@NotEmpty(message = "A descricao não pode ser vazio")
	@NotNull(message = "A descricao não pode ser null")
	private String descricao;
	
	@Column(nullable = true)
	@DateTimeFormat(pattern="yyyy-MM-dd")
//	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataHoraLancamento;
	
	@Transient
	private String valorConversao;
	
	@Column(nullable = true)
	private BigDecimal valorLancamento;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Calendar getDataHoraLancamento() {
		return dataHoraLancamento;
	}

	public void setDataHoraLancamento(Calendar dataHoraLancamento) {
		this.dataHoraLancamento = dataHoraLancamento;
	}

	public BigDecimal getValorLancamento() {
		return valorLancamento;
	}
	
	public void setValorLancamento(BigDecimal valorLancamento) {
		this.valorLancamento = valorLancamento;
	}
	
	public String getValorConversao() {
		return valorConversao;
	}
	
	public void setValorConversao(String valorConversao) {
		this.valorConversao = valorConversao;
	}
	
}
