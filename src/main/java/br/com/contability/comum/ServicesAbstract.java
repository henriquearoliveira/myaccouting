package br.com.contability.comum;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.contability.exceptions.ObjetoComDependenciaException;
import br.com.contability.exceptions.ObjetoExistenteException;
import br.com.contability.exceptions.ObjetoInexistenteException;

public class ServicesAbstract<T extends BeanIdentificavel, E extends JpaRepository<T, Long>> implements IServices<T> {

	@Autowired
	private E jpa;

	@Override
	public T atualiza(T objeto) {
		
		verificaExistencia(objeto);
		Calendar dataCadastro = get(objeto.getId()).getDataHoraCadastro();
		objeto.setDataHoraAtualizacao(Calendar.getInstance());
		objeto.setDataHoraCadastro(dataCadastro);
		
		try {
			return jpa.save(objeto);
		} catch (DataIntegrityViolationException e) {
			throw new ObjetoExistenteException("O objeto não é possível ser atualizado.");
		}
	}
	
	@Override
	public T get(Long id) {
		
		T objeto = jpa.findOne(id);
		
		if (objeto == null)
			throw new ObjetoInexistenteException("O objeto requisitado não existe.");
		
		return objeto;
	}
	
	@Override
	public T insere(T objeto) {
		
		objeto.setId(null);
		objeto.setDataHoraCadastro(Calendar.getInstance());
		
		try {
			
			return jpa.save(objeto);
			
		} catch (DataIntegrityViolationException e) {
			throw new ObjetoExistenteException("Objeto já existente.");
		}
		
	}
	
	@Override
	public void remove(Long id) {
		T objeto = get(id);
		
		try {
			jpa.delete(objeto);
		} catch (DataIntegrityViolationException e) {
			throw new ObjetoComDependenciaException("Objeto com dependencias.");
		}
		
	}
	
	public E getJpa() {
		return jpa;
	}

}