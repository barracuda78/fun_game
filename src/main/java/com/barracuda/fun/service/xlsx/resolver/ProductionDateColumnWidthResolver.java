package com.barracuda.fun.service.xlsx.resolver;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProductionDateColumnWidthResolver implements ColumnWidthResolver {

    @Override
    public boolean canResolve(SearchResultColumnHeaderName e) {
        return e == SearchResultColumnHeaderName.PRODUCTION_DATE;
    }

    @Override
    public int resolve(SearchResultColumnHeaderName e, @NonNull List<SampleDto> sampleDtoList) {
        int maxValueLength = sampleDtoList.stream()
            .map(s -> Objects.nonNull(s.getProductionDate())
                ? s.getProductionDate().toString().length() //TODO: check if date toString works
                : 0)
            .reduce(0, (l,r) -> l > r ? l : r);
        return getMaxAndAdjust(e, maxValueLength);
    }

}
