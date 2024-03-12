package com.example.documentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long workSectionNumber;

    @NotNull
    private Long elementarySectionNumber;

    @NotNull
    private BigDecimal elementarySectionArea;

    @NotNull
    private BigDecimal elementarySectionPerimeter;

    @NotNull
    @JsonProperty("pHInKCl")
    private BigDecimal pHInKCl;

    @NotNull
    @JsonProperty("pHGroup")
    private Long pHGroup;

    @NotNull
    private String granulometricSoilComposition;

    @NotNull
    private BigDecimal humusContent;

    @NotNull
    private BigDecimal caesium;

    @NotNull
    private BigDecimal strontium;

    @NotNull
    @JsonProperty("CaCO3Dose")
    private BigDecimal CaCO3Dose;

    @NotNull
    private BigDecimal limeMassDose;

    @NotNull
    private BigDecimal limeMaterialRequirement;

    @NotNull
    private Long limingPeriod;

    @NotNull
    private BigDecimal costPerArea;

    @NotNull
    private BigDecimal totalCost;
}
