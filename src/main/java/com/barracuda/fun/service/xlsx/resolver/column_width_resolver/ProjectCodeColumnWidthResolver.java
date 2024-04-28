package com.barracuda.fun.service.xlsx.resolver.column_width_resolver;

import com.barracuda.fun.service.data.DictionaryValueDto;
import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProjectCodeColumnWidthResolver implements ColumnWidthResolver {

    @Override
    public boolean canResolve(@NonNull SearchResultColumnHeaderName e) {
        return e == SearchResultColumnHeaderName.PROJECT_CODES;
    }

    @Override
    public int resolve(@NonNull SearchResultColumnHeaderName e, @NonNull List<SampleDto> sampleDtoList) {
        int maxValueLength = sampleDtoList.stream()
            .filter(s -> Objects.nonNull(s.getProjectCodes()))
            .flatMap(s -> s.getProjectCodes().stream())
            .map(DictionaryValueDto::getValue)
            .map(String::length)
            .reduce(0, (l,r) -> l > r ? l : r);
        return getMaxAndAdjust(e, maxValueLength);
    }

}