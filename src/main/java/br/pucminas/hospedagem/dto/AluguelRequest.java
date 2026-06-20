package br.pucminas.hospedagem.dto;

import br.pucminas.hospedagem.model.StatusAluguel;
import java.time.LocalDateTime;

public class AluguelRequest {

    private Long quartoId;
    private Long clienteId;
    private Long residenciaId;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private int qtdHospedes;
    private StatusAluguel status;

    public Long getQuartoId() { return quartoId; }
    public void setQuartoId(Long quartoId) { this.quartoId = quartoId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getResidenciaId() { return residenciaId; }
    public void setResidenciaId(Long residenciaId) { this.residenciaId = residenciaId; }

    public LocalDateTime getDataHoraEntrada() { return dataHoraEntrada; }
    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) { this.dataHoraEntrada = dataHoraEntrada; }

    public LocalDateTime getDataHoraSaida() { return dataHoraSaida; }
    public void setDataHoraSaida(LocalDateTime dataHoraSaida) { this.dataHoraSaida = dataHoraSaida; }

    public int getQtdHospedes() { return qtdHospedes; }
    public void setQtdHospedes(int qtdHospedes) { this.qtdHospedes = qtdHospedes; }

    public StatusAluguel getStatus() { return status; }
    public void setStatus(StatusAluguel status) { this.status = status; }
}
