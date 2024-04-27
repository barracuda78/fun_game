package com.barracuda.fun.service.xlsx.resolver;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.data.SampleVesselDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class VesselsColumnWidthResolver implements ColumnWidthResolver {

    @Override
    public boolean canResolve(SearchResultColumnHeaderName e) {
        return e == SearchResultColumnHeaderName.VESSEL;
    }

    @Override
    public int resolve(SearchResultColumnHeaderName e, @NonNull List<SampleDto> sampleDtoList) {
        int maxValueLength = sampleDtoList.stream()
            .filter(s -> Objects.nonNull(s.getVessels()))
            .flatMap(s -> s.getVessels().stream())
            .map(SampleVesselDto::getBarcode)
            .map(String::length)
            .reduce(0, (l,r) -> l > r ? l : r);
        return getMaxAndAdjust(e, maxValueLength);
    }

}
