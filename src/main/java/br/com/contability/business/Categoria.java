package br.com.contability.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.contability.comum.BeanIdentificavel;

@Entity
@Table(name = "categoria", indexes = { @Index(name = "index_categoria", columnList = "id", unique = false),
		@Index(name = "unique_descricao_usuario", columnList = "descricao,usuario_id", unique = true) })
public class Categoria extends BeanIdentificavel {

	@JsonIgnore
	@ManyToOne(optional = false)
	private Usuario usuario;

	@Column(length = 100, nullable = false)
	@NotEmpty(message = "A descricao não pode ser vazio")
	@NotNull(message = "A descricao não pode ser null")
	private String descricao;
	
	@NotNull(message = "O tipo de categoria não pode ser null")
	@Enumerated(EnumType.STRING)
	private TipoDeCategoria tipoDeCategoria;

	@Column(length = 200, nullable = true)
	private String observacao;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public TipoDeCategoria getTipoDeCategoria() {
		return tipoDeCategoria;
	}
	
	public void setTipoDeCategoria(TipoDeCategoria tipoDeCategoria) {
		this.tipoDeCategoria = tipoDeCategoria;
	}

}
