package com.barracuda.fun.service.xlsx.resolver;

import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.CELL_ADDITIONAL_WIDTH;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import java.util.List;
import lombok.NonNull;

public interface ColumnWidthResolver {

    boolean canResolve(SearchResultColumnHeaderName e);

    int resolve(SearchResultColumnHeaderName e, @NonNull List<SampleDto> sampleDtoList);

    default int getMaxAndAdjust(SearchResultColumnHeaderName e, int maxValueLength) {
        return maxValueLength > e.getLength()
            ? maxValueLength + CELL_ADDITIONAL_WIDTH
            : e.getLength() + CELL_ADDITIONAL_WIDTH;
    }

}
