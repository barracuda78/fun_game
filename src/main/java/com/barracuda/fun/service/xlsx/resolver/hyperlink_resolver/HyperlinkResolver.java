package com.barracuda.fun.service.xlsx.resolver.hyperlink_resolver;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import lombok.NonNull;

public interface HyperlinkResolver {

    boolean canResolve(SearchResultColumnHeaderName e);

    String createUrl(@NonNull String baseUrl, @NonNull SampleDto sampleDto);

    default String getEnvironment() {
        return "dev";
    }

}
