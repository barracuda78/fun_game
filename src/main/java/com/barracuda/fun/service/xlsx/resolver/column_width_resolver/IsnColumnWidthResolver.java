package com.barracuda.fun.service.xlsx.resolver.column_width_resolver;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class IsnColumnWidthResolver implements ColumnWidthResolver {

    @Override
    public boolean canResolve(@NonNull SearchResultColumnHeaderName e) {
        return e == SearchResultColumnHeaderName.ISN;
    }

    @Override
    public int resolve(@NonNull SearchResultColumnHeaderName e, @NonNull List<SampleDto> sampleDtoList) {
        int maxValueLength = sampleDtoList.stream()
            .map(s -> Objects.nonNull(s.getIsn())
                ? s.getIsn().length()
                : 0)
            .reduce(0, (l,r) -> l > r ? l : r);
        return getMaxAndAdjust(e, maxValueLength);
    }

}
