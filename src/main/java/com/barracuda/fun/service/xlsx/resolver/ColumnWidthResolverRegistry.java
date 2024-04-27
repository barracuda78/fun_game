package com.barracuda.fun.service.xlsx.resolver;

import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;

public interface ColumnWidthResolverRegistry {

    ColumnWidthResolver getResolver(SearchResultColumnHeaderName e);

}
