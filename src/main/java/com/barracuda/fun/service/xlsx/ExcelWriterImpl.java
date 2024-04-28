package com.barracuda.fun.service.xlsx;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.data.ParamsDto;
import com.barracuda.fun.service.xlsx.exception.ExcelTemporaryFileWriterException;
import com.barracuda.fun.service.xlsx.processors.SearchParametersWorksheetProcessor;
import com.barracuda.fun.service.xlsx.processors.SearchResultsWorksheetProcessor;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.dhatim.fastexcel.Workbook;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ExcelWriterImpl {

    public static final String SEARCH_RESULTS_WORKSHEET_NAME = "Search results";
    public static final String SEARCH_PARAMETERS_WORKSHEET_NAME = "Search parameters";
    public static final String SEARCH_PARAMETERS_FIRST_COLUMN_NAME = "Parameter name";
    public static final String SEARCH_PARAMETERS_SECOND_COLUMN_NAME = "Value(s)";
    public static final String NEW_LINE_DELIMITER = "\n";
    public static final String CENTER_HORIZONTAL_ALIGNMENT = "center";
    public static final int CELL_ADDITIONAL_WIDTH = 6;

    private final SearchResultsWorksheetProcessor searchResultsWorksheetProcessor;

    private final SearchParametersWorksheetProcessor searchParametersWorksheetProcessor;

    //TODO: here use sampleDTOList to write it into xslx file

    //TODO: Default file name is ‘Lumos_SearchResults_<date-time>.xlsx’

    //TODO: use temporary file
    public void write(String fileLocation, List<SampleDto> sampleDtoList, ParamsDto params) {
        //TODO: if sampleDTOList is empty -> return WorkBook with two WorkSheets.

        //TODO: use Multithreaded generation for both sheets;

        try (
            OutputStream os = Files.newOutputStream(Paths.get(fileLocation));
            Workbook workbook = new Workbook(os, "MyApplication", "1.0"))
        {
            searchResultsWorksheetProcessor.process(workbook, sampleDtoList);
            searchParametersWorksheetProcessor.process(workbook, params);
        }
        catch (IOException e) {
            throw new ExcelTemporaryFileWriterException(
                "Can not write temporary excel file to the specified location: " + fileLocation);
        }
    }

}
