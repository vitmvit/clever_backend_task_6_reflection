package org.example.observer.impl;

import org.example.model.dto.CatDto;
import org.example.observer.Observer;
import org.example.report.service.PdfService;

public class ObserverImpl implements Observer {
    private PdfService pdfService;

    public ObserverImpl(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @Override
    public void update(CatDto catDto) {
        pdfService.createPdf(catDto);
    }
}