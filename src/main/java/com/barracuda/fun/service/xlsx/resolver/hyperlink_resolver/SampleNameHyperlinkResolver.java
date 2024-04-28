package com.barracuda.fun.service.xlsx.resolver.hyperlink_resolver;

import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.SAMPLE_NAME;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SampleNameHyperlinkResolver  implements HyperlinkResolver {

    public static final String SAMPLE = "sample/";

    @Override
    public boolean canResolve(@NonNull SearchResultColumnHeaderName e) {
        return e == SAMPLE_NAME;
    }

    @Override
    public String createUrl(@NonNull SampleDto sampleDto) {
        return String.format(BASE_URL_FORMAT, getEnvironment())
            .concat(SAMPLE)
            .concat(sampleDto.getId().toString());
    }

}
