package org.example.dao.report.service;

import org.example.dao.util.CatTestData;
import org.example.model.dto.CatDto;
import org.example.report.service.PdfService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.example.constant.Constant.DIRECTORY;

public class PdfServiceTest {

    private final PdfService pdfService = new PdfService();

    @BeforeAll
    public static void setUp() {
        deleteFilesInDirectory(DIRECTORY);
    }

    @AfterAll
    public static void after() {
        deleteFilesInDirectory(DIRECTORY);
    }

    private static void deleteFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFilesInDirectory(file.getAbsolutePath());
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }

    @Test
    public void createPdfShouldCheckDirectoryForEmpty() {
        CatDto catDto = CatTestData.builder().build().buildCatDto();
        pdfService.createPdf(catDto);
        File reportDir = new File(DIRECTORY);
        Assertions.assertTrue(Objects.requireNonNull(reportDir.listFiles()).length > 0);
    }
}

