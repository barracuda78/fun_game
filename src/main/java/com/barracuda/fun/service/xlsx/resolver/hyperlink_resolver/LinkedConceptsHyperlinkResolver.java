package com.barracuda.fun.service.xlsx.resolver.hyperlink_resolver;

import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.LINKED_CONCEPTS;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.SOURCE_PROCESS;

import com.barracuda.fun.service.data.ConceptType;
import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class LinkedConceptsHyperlinkResolver implements HyperlinkResolver {

    public static final String STRAIN = "strain/";
    public static final String COMPOUND = "compound/";
    public static final String NAT_SOURCE = "nat-source/";

    @Override
    public boolean canResolve(@NonNull SearchResultColumnHeaderName e) {
        return e == LINKED_CONCEPTS;
    }

    @Override
    public String createUrl(@NonNull String baseUrl, @NonNull SampleDto sampleDto) {
        return baseUrl
            .concat(getUrlPart(sampleDto.getLinkedConcepts().get(0).getConceptType())) //TODO: get not only first link. Merge cells. use multiple links.
            .concat(sampleDto.getLinkedConcepts().get(0).getId().toString());
    }

    private String getUrlPart(ConceptType conceptType) {
        return switch (conceptType) {
            case strain -> STRAIN;
            case chemicalCompound -> COMPOUND;
            case naturalSource -> NAT_SOURCE;
        };
    }

}
