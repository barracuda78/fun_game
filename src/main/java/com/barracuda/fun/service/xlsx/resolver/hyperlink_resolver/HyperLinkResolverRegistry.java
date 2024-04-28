package com.barracuda.fun.service.xlsx.resolver.hyperlink_resolver;

import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import lombok.NonNull;

public interface HyperLinkResolverRegistry {

    HyperlinkResolver getRegistry(@NonNull SearchResultColumnHeaderName e);

}
