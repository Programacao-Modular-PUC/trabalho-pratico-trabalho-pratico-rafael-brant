package br.pucminas.hospedagem;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestReportListener implements TestExecutionListener {

    private static final Path REPORT_PATH = Path.of("src", "test", "resources", "relatorio", "test-report.txt");
    private final AtomicInteger testsFound = new AtomicInteger();
    private final AtomicInteger testsSucceeded = new AtomicInteger();
    private final AtomicInteger testsFailed = new AtomicInteger();
    private final AtomicInteger testsAborted = new AtomicInteger();
    private final AtomicInteger testsSkipped = new AtomicInteger();

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        try {
            Files.createDirectories(REPORT_PATH.getParent());
            Files.deleteIfExists(REPORT_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível limpar o relatório de testes.", e);
        }
    }

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
        if (!testIdentifier.isTest()) {
            return;
        }
        testsFound.incrementAndGet();
        testsSkipped.incrementAndGet();
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (!testIdentifier.isTest()) {
            return;
        }
        testsFound.incrementAndGet();
        switch (testExecutionResult.getStatus()) {
            case SUCCESSFUL -> testsSucceeded.incrementAndGet();
            case FAILED -> testsFailed.incrementAndGet();
            case ABORTED -> testsAborted.incrementAndGet();
        }
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        writeReport();
    }

    private void writeReport() {
        List<String> lines = List.of(
                "Relatório de Testes - " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()),
                "===================================================",
                "Total de testes executados: " + testsFound.get(),
                "Sucesso: " + testsSucceeded.get(),
                "Falha: " + testsFailed.get(),
                "Abortado: " + testsAborted.get(),
                "Ignorado: " + testsSkipped.get(),
                ""
        );

        try {
            Files.write(REPORT_PATH, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível escrever o relatório de testes.", e);
        }
    }

    public void gerarRelatorio() {
        try {
            Files.createDirectories(REPORT_PATH.getParent());
            Files.deleteIfExists(REPORT_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível preparar o relatório de testes.", e);
        }
        writeReport();
    }
}
