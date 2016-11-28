package br.com.contability.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.contability.comum.BeanIdentificavel;

@Entity
@Table(name = "conta", indexes = { @Index(name = "index_conta", columnList = "id", unique = false)})
public class Conta extends BeanIdentificavel{
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@Column(length = 100, nullable = false)
	@NotEmpty(message = "A descricao não pode ser vazio")
	@NotNull(message = "A descricao não pode ser null")
	private String descricao;
	
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

}
