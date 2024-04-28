package com.barracuda.fun.service.xlsx.resolver.column_width_resolver;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class LatinNameColumnWidthResolver implements ColumnWidthResolver {

    @Override
    public boolean canResolve(@NonNull SearchResultColumnHeaderName e) {
        return e == SearchResultColumnHeaderName.LATIN_NAME;
    }

    @Override
    public int resolve(@NonNull SearchResultColumnHeaderName e, @NonNull List<SampleDto> sampleDtoList) {
        int maxValueLength = sampleDtoList.stream()
            .filter(s -> Objects.nonNull(s.getLatinNames()))
            .flatMap(s -> s.getLatinNames().stream())
            .map(String::length)
            .reduce(0, (l,r) -> l > r ? l : r);
        return getMaxAndAdjust(e, maxValueLength);
    }

}
