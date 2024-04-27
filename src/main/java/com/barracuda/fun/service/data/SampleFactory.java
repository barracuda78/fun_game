package com.barracuda.fun.service.data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class SampleFactory {

    public static SampleDto.SampleDtoBuilder createSomeDTOBuilder (int param) {
        return SampleDto.builder()
            .id(UUID.randomUUID())
            .name("some sample name" + param)
            .process(ProcessInfoShortDto.builder()
                .id(UUID.randomUUID())
                .elnId(UUID.randomUUID())
                .name("some process name" + param)
                .build())
            .isn("some isn" + param)
            .latinNames(List.of("some latin name" + param, "other latin name" + param))
            .projectCodes(List.of(
                DictionaryValueDto.builder()
                    .id(UUID.randomUUID())
                    .code("some code" + param)
                    .value("some value" + param)
                    .build()))
            .productionDate(LocalDate.now())
            .vessels(List.of(SampleVesselDto.builder()
                    .id(UUID.randomUUID())
                    .barcode("some barcode" + param)
                    .type(VesselTypeShortDto.builder()
                        .id(UUID.randomUUID())
                        .name("some vessel type name" + param)
                        .build())
                .build()))
            .linkedConcepts(List.of(
                SampleLinkedConceptShortDto.builder()
                    .id(UUID.randomUUID())
                    .name("some concept name" + param)
                    .conceptType(
                        param > 1
                        ? ConceptType.naturalSource
                        : ConceptType.chemicalCompound)
                    .build()
            ));
    }

    public static ParamsDto.ParamsDtoBuilder createParams() {
        return ParamsDto.builder()
            .sampleNames(List.of("AT_sample_171113964432223936", "AT_sample_171113964432223938"))
            .projectNames(List.of("AVAKITT DUO (GEA1499)", "Biostimulant discovery (DES-BIOSTM)", "Business Continuity"))
            .processTemplateNames(List.of("Batch Centrifugation", "Extra Freeze"))
            .conceptType(ConceptType.chemicalCompound)
            .conceptNames(List.of("Strain 3 for research target test", "Chemical compound 2"));
    }

}
