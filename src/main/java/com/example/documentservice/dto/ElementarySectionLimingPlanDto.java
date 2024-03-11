package com.example.documentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ElementarySectionLimingPlanDto {

    private Long workSectionNumber;

    private Long elementarySectionNumber;

    private BigDecimal elementarySectionArea;

    private BigDecimal elementarySectionPerimeter;

    private BigDecimal pHInKCl;

    private Long pHGroup;

    private String granulometricSoilComposition;

    private BigDecimal humusContent;

    private BigDecimal caesium;

    private BigDecimal strontium;

    private BigDecimal CaCO3Dose;

    private BigDecimal limeMassDose;

    private BigDecimal limeMaterialRequirement;

    private Long limingPeriod;

    private BigDecimal costPerArea;

    private BigDecimal totalCost;
}
