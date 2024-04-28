package com.barracuda.fun.service.xlsx.processors;


import com.barracuda.fun.service.data.SampleDto;
import java.util.List;
import lombok.NonNull;
import org.dhatim.fastexcel.Workbook;

public interface SearchResultsWorksheetProcessor {

    void process(@NonNull Workbook workbook, @NonNull List<SampleDto> sampleDtoList);

}
