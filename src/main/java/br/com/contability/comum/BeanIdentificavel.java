package br.com.contability.comum;

import java.util.Calendar;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class BeanIdentificavel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
//	@DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss")
//	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "A data bean não pode ser null")
	private Calendar dataHoraCadastro, dataHoraAtualizacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(Calendar dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public Calendar getDataHoraAtualizacao() {
		return dataHoraAtualizacao;
	}

	public void setDataHoraAtualizacao(Calendar dataHoraAtualizacao) {
		this.dataHoraAtualizacao = dataHoraAtualizacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanIdentificavel other = (BeanIdentificavel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
