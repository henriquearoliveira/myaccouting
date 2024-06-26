package br.com.contability.comum;

import br.com.contability.exceptions.ObjetoComDependenciaException;
import br.com.contability.exceptions.ObjetoExistenteException;
import br.com.contability.exceptions.ObjetoExistenteExceptionModel;
import br.com.contability.exceptions.ObjetoInexistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class ServicesAbstract<T extends BeanIdentificavel, E extends JpaRepository<T, Long>> implements IServices<T> {

    @Autowired
    private E jpa;

    @Override
    public T atualiza(T objeto, Boolean isApiProgram) {
        final Optional<Boolean> optional = Optional.ofNullable(isApiProgram);

        verificaExistencia(objeto, isApiProgram);

        final LocalDateTime dataCadastro = get(objeto.getId(), isApiProgram).getDataHoraCadastro();
        objeto.setDataHoraAtualizacao(LocalDateTime.now());
        objeto.setDataHoraCadastro(dataCadastro);

        try {
            return jpa.save(objeto);
        } catch (DataIntegrityViolationException e) {
            if (optional.isEmpty()) {
                throw new ObjetoExistenteException("O objeto chegou vazio, por favor tente novamente.");
            }
            throw new ObjetoExistenteException("O objeto não é possível ser atualizado.");
        }
    }

    @Override
    public T get(Long id, Boolean isApiProgram) {
        final Optional<Boolean> optional = Optional.ofNullable(isApiProgram);

        final Optional<T> objeto = jpa.findById(id);

        if (objeto.isEmpty()) {
            if (optional.isEmpty())
                throw new ObjetoExistenteException("O objeto requisitado não existe.");

            throw new ObjetoInexistenteException("O objeto requisitado não existe.");
        }

        return objeto.get();
    }

    @Override
    public T insere(T objeto, Boolean isApiProgram) {
        final Optional<Boolean> optional = Optional.ofNullable(isApiProgram);

        objeto.setId(null);
        objeto.setDataHoraCadastro(LocalDateTime.now());

        try {

            return jpa.save(objeto);

        } catch (DataIntegrityViolationException e) {
            if (optional.isEmpty()) {
                throw new ObjetoExistenteExceptionModel("Objeto já existente.");
            }
            throw new ObjetoExistenteException("Objeto já existente.");
        }

    }

    @Override
    public void remove(Long id, Boolean isApiProgram) {
        final T objeto = get(id, isApiProgram);
        Optional<Boolean> optional = Optional.ofNullable(isApiProgram);

        try {
            jpa.delete(objeto);
        } catch (DataIntegrityViolationException e) {
            if (optional.isEmpty()) {
                throw new ObjetoExistenteExceptionModel("Objeto com dependencias.");
            }
            throw new ObjetoComDependenciaException("Objeto com dependencias.");
        }

    }

    public E getJpa() {
        return jpa;
    }
}