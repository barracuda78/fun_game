package com.barracuda.fun.service.xlsx.resolver.hyperlink_resolver;

import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.SOURCE_PROCESS;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SourceProcessHyperlinkResolver implements HyperlinkResolver {

    public static final String ELN = "eln/";
    public static final String PROCESS = "/process/";

    @Override
    public boolean canResolve(@NonNull SearchResultColumnHeaderName e) {
        return e == SOURCE_PROCESS;
    }

    @Override
    public String createUrl(@NonNull String baseUrl, @NonNull SampleDto sampleDto) {
        return baseUrl
            .concat(ELN)
            .concat(sampleDto.getProcess().getElnId().toString())
            .concat(PROCESS)
            .concat(sampleDto.getProcess().getId().toString());
    }

}
