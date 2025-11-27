package com.gustavoecia.dicionarioinventado.repository;

import com.gustavoecia.dicionarioinventado.model.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PalavraRepository extends JpaRepository<Palavra, Long> {
    Optional<Palavra> findByPalavraIgnoreCase(String palavra);
}

