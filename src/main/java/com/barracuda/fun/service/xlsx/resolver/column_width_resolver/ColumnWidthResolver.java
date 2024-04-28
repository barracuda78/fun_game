package com.barracuda.fun.service.xlsx.resolver.column_width_resolver;

import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.CELL_ADDITIONAL_WIDTH;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import java.util.List;
import lombok.NonNull;

public interface ColumnWidthResolver {

    boolean canResolve(@NonNull SearchResultColumnHeaderName e);

    int resolve(@NonNull SearchResultColumnHeaderName e, @NonNull List<SampleDto> sampleDtoList);

    default int getMaxAndAdjust(@NonNull SearchResultColumnHeaderName e, int maxValueLength) {
        return maxValueLength > e.getLength()
            ? maxValueLength + CELL_ADDITIONAL_WIDTH
            : e.getLength() + CELL_ADDITIONAL_WIDTH;
    }

}
