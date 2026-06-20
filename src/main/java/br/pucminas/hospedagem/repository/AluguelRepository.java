package br.pucminas.hospedagem.repository;

import br.pucminas.hospedagem.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    List<Aluguel> findByQuartoTipoQuarto(String tipoQuarto);

    List<Aluguel> findByClienteId(Long clienteId);

    @Query("SELECT a FROM Aluguel a WHERE a.quarto.id = :quartoId " +
           "AND a.status = 'ATIVO' " +
           "AND a.dataHoraEntrada < :saida AND a.dataHoraSaida > :entrada")
    List<Aluguel> findAlugueisConflitantes(
        @Param("quartoId") Long quartoId,
        @Param("entrada") LocalDateTime entrada,
        @Param("saida") LocalDateTime saida);
}
