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
public class ProcessInfoShortDto {
    private UUID id;
    private UUID elnId;
    private String name;
}
