package br.pucminas.hospedagem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestReportListenerTest {

    private static final Path REPORT_PATH = Path.of("src", "test", "resources", "relatorio", "test-report.txt");

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(REPORT_PATH);
    }

    @Test
    void deveGerarRelatorioTxtAoFinalizarOsTestes() throws IOException {
        Files.deleteIfExists(REPORT_PATH);
        TestReportListener listener = new TestReportListener();

        listener.gerarRelatorio();

        assertTrue(Files.exists(REPORT_PATH), "Arquivo de relatório deve existir");

        String content = Files.readString(REPORT_PATH);
        assertTrue(content.contains("Total de testes executados:"), "Relatório deve conter o total de testes");
        assertTrue(content.contains("Sucesso: 0"), "Relatório deve indicar 0 testes bem sucedidos");
        assertTrue(content.contains("Falha: 0"), "Relatório deve indicar 0 falhas");
    }
}
