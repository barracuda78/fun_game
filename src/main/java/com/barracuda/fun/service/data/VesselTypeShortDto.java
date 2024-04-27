package com.barracuda.fun.service.data;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VesselTypeShortDto {
    private UUID id;
    private String name;
}
