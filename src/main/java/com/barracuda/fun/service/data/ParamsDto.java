package com.barracuda.fun.service.data;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ParamsDto {

    List<String> sampleNames;

    List<String> projectNames;

    List<String> processTemplateNames;

    ConceptType conceptType;

    List<String> conceptNames;

}