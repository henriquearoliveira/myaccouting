package br.com.contability.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.contability.business.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	@Query("SELECT c FROM Categoria c WHERE c.usuario.id = ?1")
	public List<Categoria> seleciona(Long usuario);

	@Query("SELECT c FROM Categoria c WHERE c.id = ?1 AND c.usuario.id = ?2")
	public Categoria getCategorias(Long id, Long idUsuario);

}
