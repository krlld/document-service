package com.example.documentservice.service.impl;

import com.example.documentservice.dto.ElementarySectionLimingPlanDto;
import com.example.documentservice.dto.LimingRequestDto;
import com.example.documentservice.service.LimingDocumentService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LimingDocumentServiceImpl implements LimingDocumentService {

    public static final int COLUMNS_NUMBER = 16;

    public static final String[] HEADERS = {
            "№ рабо-\nчего уч.",
            "№ элем. уч.",
            "Площ. элем. уч., га",
            "Перим. элем. уч.",
            "pH в KCl",
            "Группа pH",
            "Грану-\nломе-\nтричес-\nкий состав почвы",
            "Содер-\nжание гумуса, %",
            "Цезий, Ku/km2",
            "Строн-\nций, Ku/km2",
            "Доза CaCO3, т/га",
            "Доза извес.\nмат. в ф.в., т/га",
            "Треб. изв. мате-\nриала тонн в ф.в.",
            "Срок извест-\nкова-\nния",
            "Расце-\nнка на 1 га, руб.",
            "Всего стои-\nмость работ, руб."
    };

    BaseFont unicode;

    {
        try {
            unicode = BaseFont.createFont(
                    "fonts/ofont.ru_Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    Font font = new Font(unicode, 8);

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
            document.setMargins(1, 1, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            addHeader(document, limingRequestDto);
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

    private void addHeader(Document document, LimingRequestDto limingRequestDto) throws DocumentException {
        String header = """
                ПЛАН И СМЕТНО - ФИНАНСОВЫЙ РАСЧЕТ ПО ИЗВЕСТКОВАНИЮ КИСЛЫХ ПОЧВ
                СЕЛЬСКОХОЗЯЙСТВЕННЫХ ЗЕМЕЛЬ
                НА 2024 г.
                """;
        Paragraph headerParagraph = new Paragraph(header, font);
        headerParagraph.setAlignment(Element.ALIGN_CENTER);
        headerParagraph.setSpacingAfter(10);
        document.add(headerParagraph);

        String info = "%s          %s          %s".formatted(
                limingRequestDto.getOrganizationName(),
                limingRequestDto.getDistrictName(),
                limingRequestDto.getRegionName()
        );
        Paragraph infoParagraph = new Paragraph(info, font);
        infoParagraph.setAlignment(Element.ALIGN_CENTER);
        infoParagraph.setSpacingAfter(10);
        document.add(infoParagraph);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of(HEADERS)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(0.1f);
                    Phrase phrase = new Phrase(columnTitle, font);
                    header.setPhrase(phrase);
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<ElementarySectionLimingPlanDto> rows) {
        rows.forEach(row -> {
            table.addCell(new Phrase(row.getWorkSectionNumber().toString(), font));
            table.addCell(new Phrase(row.getElementarySectionNumber().toString(), font));
            table.addCell(new Phrase(row.getElementarySectionArea().setScale(1, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getElementarySectionPerimeter().setScale(1, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getPHInKCl().setScale(2, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getPHGroup().toString(), font));
            table.addCell(new Phrase(row.getGranulometricSoilComposition(), font));
            table.addCell(new Phrase(row.getHumusContent().setScale(2, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getCaesium().setScale(2, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getStrontium().setScale(2, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getCaCO3Dose().setScale(1, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getLimeMassDose().setScale(1, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getLimeMaterialRequirement().setScale(1, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getLimingPeriod().toString(), font));
            table.addCell(new Phrase(row.getCostPerArea().setScale(2, RoundingMode.CEILING).toString(), font));
            table.addCell(new Phrase(row.getTotalCost().setScale(2, RoundingMode.CEILING).toString(), font));
        });
        table.addCell(new Phrase("Итого", font));
        table.addCell(new Phrase());
        table.addCell(new Phrase(
                rows.stream()
                        .map(ElementarySectionLimingPlanDto::getElementarySectionArea)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO).setScale(1, RoundingMode.CEILING).toString(), font));
        table.addCell(new Phrase());
        table.addCell(new Phrase());
        table.addCell(new Phrase());
        table.addCell(new Phrase());
        table.addCell(new Phrase());
        table.addCell(new Phrase());
        table.addCell(new Phrase());
        table.addCell(new Phrase());
        table.addCell(new Phrase(
                rows.stream()
                        .map(ElementarySectionLimingPlanDto::getLimeMassDose)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO)
                        .divide(BigDecimal.valueOf(rows.size()), 1, RoundingMode.CEILING).toString(), font));
        table.addCell(new Phrase(
                rows.stream()
                        .map(ElementarySectionLimingPlanDto::getLimeMaterialRequirement)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO).setScale(1, RoundingMode.CEILING).toString(), font));
        table.addCell(new Phrase());
        table.addCell(new Phrase(
                rows.stream()
                        .map(ElementarySectionLimingPlanDto::getCostPerArea)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO)
                        .divide(BigDecimal.valueOf(rows.size()), 2, RoundingMode.CEILING).toString(), font));
        table.addCell(new Phrase(
                rows.stream()
                        .map(ElementarySectionLimingPlanDto::getTotalCost)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO).setScale(2, RoundingMode.CEILING).toString(), font));
    }
}
