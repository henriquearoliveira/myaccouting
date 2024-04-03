package br.com.contability.comum;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface IResources<T extends BeanIdentificavel> {

    @PutMapping(value = "/{id}")
    default ResponseEntity<Void> atualiza(Long id, T objeto) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{id}")
    default ResponseEntity<T> get(Long id) {
        return null;
    }

    @PostMapping
    default ResponseEntity<Void> insere(T objeto) {
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    default ResponseEntity<Void> remove(Long id) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    default ResponseEntity<List<T>> seleciona() {
        return null;
    }
}
