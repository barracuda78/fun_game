package com.barracuda.fun.service.xlsx.resolver.column_width_resolver;

import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import lombok.NonNull;

public interface ColumnWidthResolverRegistry {

    ColumnWidthResolver getResolver(@NonNull SearchResultColumnHeaderName e);

}
