package com.example.documentservice.controller;

import com.example.documentservice.dto.LimingRequestDto;
import com.example.documentservice.service.LimingDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class DocumentController {

    public final LimingDocumentService limingDocumentService;

    @GetMapping(value = "/liming-document", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource createLimingDocument(@Valid @RequestBody(required = false) LimingRequestDto limingRequestDto) {
        return limingDocumentService.createDocument(limingRequestDto);
    }
}
