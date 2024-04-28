package com.barracuda.fun.service.xlsx.resolver.hyperlink_resolver;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import lombok.NonNull;

public interface HyperlinkResolver {

    String BASE_URL_FORMAT = "https://%s.lumos.cloud.syngenta.org/";

    boolean canResolve(SearchResultColumnHeaderName e);

    String createUrl(@NonNull SampleDto sampleDto);

    default String getEnvironment() {
        return "dev";
    }

}
