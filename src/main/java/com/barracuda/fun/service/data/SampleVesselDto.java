package com.barracuda.fun.service.data;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleVesselDto {
    private UUID id;
    private String barcode;
    private VesselTypeShortDto type;
}
