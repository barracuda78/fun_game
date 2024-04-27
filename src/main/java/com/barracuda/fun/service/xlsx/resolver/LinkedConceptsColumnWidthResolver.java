package com.barracuda.fun.service.xlsx.resolver;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class LinkedConceptsColumnWidthResolver implements ColumnWidthResolver {

    @Override
    public boolean canResolve(SearchResultColumnHeaderName e) {
        return e == SearchResultColumnHeaderName.LINKED_CONCEPTS;
    }

    @Override
    public int resolve(SearchResultColumnHeaderName e, @NonNull List<SampleDto> sampleDtoList) {
        int maxValueLength = sampleDtoList.stream()
            .filter(s -> Objects.nonNull(s.getLinkedConcepts()))
            .flatMap(s -> s.getLinkedConcepts().stream())
            .map(lc -> lc.getName() + " (" + lc.getConceptType().name() + ")") //TODO: do not duplicate it!
            .map(String::length)
            .reduce(0, (l,r) -> l > r ? l : r);
        return getMaxAndAdjust(e, maxValueLength);
    }

}
