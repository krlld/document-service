package com.example.documentservice.service.impl;

import com.example.documentservice.dto.ElementarySectionLimingPlanDto;
import com.example.documentservice.dto.LimingRequestDto;
import com.example.documentservice.service.LimingDocumentService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LimingDocumentServiceImpl implements LimingDocumentService {

    public static final int COLUMNS_NUMBER = 16;

    public static final String[] HEADERS = {
            "Work Section Number",
            "Elementary Section Number",
            "Elementary Section Area",
            "Elementary Section Perimeter",
            "pH in KCl",
            "pH Group",
            "Granulometric Soil Composition",
            "Humus Content",
            "Caesium",
            "Strontium",
            "CaCO3 Dose",
            "Lime Mass Dose",
            "Lime Material Requirement",
            "Liming Period",
            "Cost per Area",
            "Total Cost"
    };

    @Override
    public Resource createDocument(LimingRequestDto limingRequestDto) {
        try {
            String dirName = "documents";
            File theDir = new File(dirName);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String fileName = LocalDateTime.now() + ".pdf";
            String filePath = String.valueOf(Paths.get(dirName, fileName));
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            PdfPTable table = new PdfPTable(COLUMNS_NUMBER);
            addTableHeader(table);
            addRows(table, limingRequestDto.getElementarySectionLimingPlans());

            document.add(table);
            document.close();
            return new UrlResource("file:" + filePath);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of(HEADERS)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<ElementarySectionLimingPlanDto> rows) {
        rows.forEach(row -> {
            table.addCell(row.getWorkSectionNumber().toString());
            table.addCell(row.getElementarySectionNumber().toString());
            table.addCell(row.getElementarySectionArea().toString());
            table.addCell(row.getPHInKCl().toString());
            table.addCell(row.getPHGroup().toString());
            table.addCell(row.getGranulometricSoilComposition());
            table.addCell(row.getHumusContent().toString());
            table.addCell(row.getCaesium().toString());
            table.addCell(row.getStrontium().toString());
            table.addCell(row.getCaCO3Dose().toString());
            table.addCell(row.getLimeMassDose().toString());
            table.addCell(row.getLimeMassDose().toString());
            table.addCell(row.getLimingPeriod().toString());
            table.addCell(row.getCostPerArea().toString());
            table.addCell(row.getCostPerArea().toString());
            table.addCell(row.getTotalCost().toString());
        });
    }
}
