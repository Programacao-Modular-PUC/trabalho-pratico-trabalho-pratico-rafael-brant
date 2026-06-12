package br.pucminas.hospedagem.repository;

import br.pucminas.hospedagem.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    List<Aluguel> findByQuartoTipoQuarto(String tipoQuarto);

    List<Aluguel> findByClienteId(Long clienteId);
}
