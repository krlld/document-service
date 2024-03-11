package com.example.documentservice.service;

import com.example.documentservice.dto.LimingRequestDto;
import org.springframework.core.io.Resource;

public interface LimingDocumentService {

    Resource createDocument(LimingRequestDto limingRequestDto);
}
