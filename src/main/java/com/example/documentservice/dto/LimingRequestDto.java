package com.example.documentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LimingRequestDto {

    private String organizationName;

    private String districtName;

    private String regionName;

    private List<ElementarySectionLimingPlanDto> elementarySectionLimingPlans;
}
