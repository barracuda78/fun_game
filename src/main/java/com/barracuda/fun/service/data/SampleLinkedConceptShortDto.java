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
public class SampleLinkedConceptShortDto {
    private UUID id;
    private ConceptType conceptType;
    private String name;
}
