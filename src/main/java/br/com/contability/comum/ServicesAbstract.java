package br.com.contability.comum;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.contability.exceptions.ObjetoComDependenciaExceptionModel;
import br.com.contability.exceptions.ObjetoExistenteExceptionModel;
import br.com.contability.exceptions.ObjetoInexistenteExceptionModel;

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
			optional.ifPresent(i -> {
				throw new ObjetoExistenteExceptionModel("O obje..");
			});
			throw new ObjetoExistenteExceptionModel("O objeto não é possível ser atualizado.");
		}
	}

	@Override
	public T get(Long id, Boolean webOrApi) {
		Optional<Boolean> optional = Optional.ofNullable(webOrApi);

		T objeto = jpa.findOne(id);

		if (objeto == null){
			optional.ifPresent(i -> {
				throw new ObjetoExistenteExceptionModel("O obje..");
			});
			
			throw new ObjetoInexistenteExceptionModel("O objeto requisitado não existe.");
			
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
			optional.ifPresent(i -> {
				throw new ObjetoExistenteExceptionModel("O obje..");
			});
			throw new ObjetoExistenteExceptionModel("Objeto já existente.");
		}

	}

	@Override
	public void remove(Long id, Boolean webOrApi) {
		T objeto = get(id, webOrApi);
		Optional<Boolean> optional = Optional.ofNullable(webOrApi);

		try {
			jpa.delete(objeto);
		} catch (DataIntegrityViolationException e) {
			optional.ifPresent(i -> {
				throw new ObjetoExistenteExceptionModel("O obje..");
			});
			throw new ObjetoComDependenciaExceptionModel("Objeto com dependencias.");
		}

	}

	public E getJpa() {
		return jpa;
	}

}