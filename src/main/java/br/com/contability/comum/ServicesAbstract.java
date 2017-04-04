package br.com.contability.comum;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.contability.exceptions.ObjetoComDependenciaException;
import br.com.contability.exceptions.ObjetoExistenteException;
import br.com.contability.exceptions.ObjetoExistenteExceptionModel;
import br.com.contability.exceptions.ObjetoInexistenteException;

public class ServicesAbstract<T extends BeanIdentificavel, E extends JpaRepository<T, Long>> implements IServices<T> {

	@Autowired
	private E jpa;
	
	@Override
	public T atualiza(T objeto, Boolean webOrApi) {
		Optional<Boolean> optional = Optional.ofNullable(webOrApi);
		
		verificaExistencia(objeto, webOrApi);
		Calendar dataCadastro = get(objeto.getId(), webOrApi).getDataHoraCadastro();
		objeto.setDataHoraAtualizacao(Calendar.getInstance());
		objeto.setDataHoraCadastro(dataCadastro);

		try {
			return jpa.save(objeto);
		} catch (DataIntegrityViolationException e) {
			optional.orElseThrow(() -> new ObjetoExistenteExceptionModel("O objeto não é possível ser atualizado."));
			throw new ObjetoExistenteException("O objeto não é possível ser atualizado.");
		}
	}

	@Override
	public T get(Long id, Boolean webOrApi) {
		Optional<Boolean> optional = Optional.ofNullable(webOrApi);

		T objeto = jpa.findOne(id);

		if (objeto == null){
			optional.orElseThrow(() -> new ObjetoExistenteExceptionModel("O objeto requisitado não existe."));
			
			throw new ObjetoInexistenteException("O objeto requisitado não existe.");
			
		}

		return objeto;
	}

	@Override
	public T insere(T objeto, Boolean webOrApi) {
		Optional<Boolean> optional = Optional.ofNullable(webOrApi);

		objeto.setId(null);
		objeto.setDataHoraCadastro(Calendar.getInstance());

		try {

			return jpa.save(objeto);

		} catch (DataIntegrityViolationException e) {
			optional.orElseThrow(() -> new ObjetoExistenteExceptionModel("Objeto já existente."));
			throw new ObjetoExistenteException("Objeto já existente.");
		}

	}

	@Override
	public void remove(Long id, Boolean webOrApi) {
		T objeto = get(id, webOrApi);
		Optional<Boolean> optional = Optional.ofNullable(webOrApi);

		try {
			jpa.delete(objeto);
		} catch (DataIntegrityViolationException e) {
			optional.orElseThrow(() -> new ObjetoExistenteExceptionModel("Objeto com dependencias."));
			throw new ObjetoComDependenciaException("Objeto com dependencias.");
		}

	}

	public E getJpa() {
		return jpa;
	}

}