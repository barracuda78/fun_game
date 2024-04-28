package com.barracuda.fun.service.xlsx.processors;

import com.barracuda.fun.service.data.ParamsDto;
import lombok.NonNull;
import org.dhatim.fastexcel.Workbook;

public interface SearchParametersWorksheetProcessor {

    void process(@NonNull Workbook workbook, @NonNull ParamsDto params);

}
