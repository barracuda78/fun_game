package com.barracuda.fun.service.data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
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






