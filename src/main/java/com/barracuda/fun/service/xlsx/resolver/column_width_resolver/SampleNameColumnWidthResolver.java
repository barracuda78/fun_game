package com.barracuda.fun.service.xlsx.resolver.column_width_resolver;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SampleNameColumnWidthResolver implements ColumnWidthResolver {

    @Override
    public boolean canResolve(@NonNull SearchResultColumnHeaderName e) {
        return e == SearchResultColumnHeaderName.SAMPLE_NAME;
    }

    @Override
    public int resolve(@NonNull SearchResultColumnHeaderName e, @NonNull List<SampleDto> sampleDtoList) {
        int maxValueLength = sampleDtoList.stream()
            .map(s -> s.getName().length())
            .reduce(0, (l,r) -> l > r ? l : r);
        return getMaxAndAdjust(e, maxValueLength);
    }

}
