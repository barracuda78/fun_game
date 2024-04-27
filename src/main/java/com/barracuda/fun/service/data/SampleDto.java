package com.barracuda.fun.service.data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
public class SampleDto {
    UUID id;
    String name;
    ProcessInfoShortDto process;
    String isn;
    List<String> latinNames;
    List<DictionaryValueDto> projectCodes;
    DictionaryValueDto physicalForm;
    LocalDate productionDate;
    List<SampleVesselDto> vessels;
    List<SampleLinkedConceptShortDto> linkedConcepts;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ProcessInfoShortDto {
    private UUID id;
    private UUID elnId;
    private String name;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class DictionaryValueDto {
    private UUID id;
    private String code;
    private String value;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class SampleVesselDto {
    private UUID id;
    private String barcode;
    private VesselTypeShortDto type;
}

@Data
@Builder
class VesselTypeShortDto {
    private UUID id;
    private String name;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class SampleLinkedConceptShortDto {
    private UUID id;
    private ConceptType conceptType;
    private String name;
}
