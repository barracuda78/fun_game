package com.barracuda.fun.service.xlsx.resolver.hyperlink_resolver;

import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import com.barracuda.fun.service.xlsx.exception.HyperlinkResolverNotFoundException;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HyperlinkResolverRegistryImpl implements HyperLinkResolverRegistry {

    private final List<HyperlinkResolver> resolvers;

    @Override
    public HyperlinkResolver getRegistry(@NonNull SearchResultColumnHeaderName e) {
        return resolvers.stream()
            .filter(r -> r.canResolve(e))
            .findFirst()
            .orElseThrow(() -> new HyperlinkResolverNotFoundException(
                "HyperlinkResolver not found for column: " + e.getCode()));
    }

}
