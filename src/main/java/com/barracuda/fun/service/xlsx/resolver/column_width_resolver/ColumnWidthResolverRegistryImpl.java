package com.barracuda.fun.service.xlsx.resolver.column_width_resolver;

import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import com.barracuda.fun.service.xlsx.exception.ColumnWidthResolverNotFoundException;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ColumnWidthResolverRegistryImpl implements  ColumnWidthResolverRegistry {

    private final List<ColumnWidthResolver> resolvers;

    @Override
    public ColumnWidthResolver getResolver(@NonNull SearchResultColumnHeaderName e) {
        return resolvers.stream()
            .filter(r -> r.canResolve(e))
            .findFirst()
            .orElseThrow(() -> new ColumnWidthResolverNotFoundException(
                "ColumnWidthResolver not found for column: " + e.getCode()));
    }
}
