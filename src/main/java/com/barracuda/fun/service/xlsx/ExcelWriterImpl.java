package com.barracuda.fun.service.xlsx;

import com.barracuda.fun.service.data.ConceptType;
import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.data.ParamsDto;
import com.barracuda.fun.service.xlsx.processors.TableHeaderProcessor;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


@Service
@RequiredArgsConstructor
public class ExcelWriterImpl {

    public static final String SEARCH_RESULTS_WORKSHEET_NAME = "Search results";

    public static final String SEARCH_PARAMETERS_WORKSHEET_NAME = "Search parameters";
    public static final String SEARCH_PARAMETERS_FIRST_COLUMN_NAME = "Parameter name";
    public static final String SEARCH_PARAMETERS_SECOND_COLUMN_NAME = "Value(s)";

    private final TableHeaderProcessor tableHeaderProcessor;


    //TODO: here use sampleDTOList to write it into xslx file
    public void writeExcel(String fileLocation, List<SampleDto> sampleDtoList, ParamsDto params) {
        //TODO: if sampleDTOList is empty -> return WorkBook with two WorkSheets.

        //TODO: use Multithreaded generation for both sheets;

        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation)); Workbook workbook = new Workbook(os, "MyApplication", "1.0")) {
            //worksheet_1 setup:
            Worksheet worksheet_1 = workbook.newWorksheet(SEARCH_RESULTS_WORKSHEET_NAME);
            worksheet_1.width(0, 25);
            worksheet_1.width(1, 15);

            //worksheet_1 headers style
            worksheet_1.range(0, 0, 0, 1).style().wrapText(true).set();

            //worksheet_1 headers content:
            worksheet_1.value(0, 0, SEARCH_PARAMETERS_FIRST_COLUMN_NAME);
            worksheet_1.value(0, 1, SEARCH_PARAMETERS_SECOND_COLUMN_NAME);

            //worksheet_1 content style
            worksheet_1.range(2, 0, 2, 1).style().wrapText(true).set();

            //worksheet_1 content content
            worksheet_1.value(2, 0, "AT_sample_171113964432223936");
            worksheet_1.value(2, 1, "AVAKITT DUO (GEA1499)");


            //--------------------------------------------------------------------------------------
            Worksheet worksheet_2 = workbook.newWorksheet(SEARCH_PARAMETERS_WORKSHEET_NAME);
            createParametersTableHeader(worksheet_2, params);
            fillParametersTableData(worksheet_2, params);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Methods to handle results table (worksheet 1) :
    public void createSearchResultTableHeader(Worksheet worksheet_2, List<SampleDto> sampleDtoList) {

    }


    // Methods to handle parameters table (worksheet 2) :
    private void createParametersTableHeader(Worksheet worksheet_2, ParamsDto params) {
        var columnWidth = calculateMaxColumnWidth(params);
        var parameterNamesColumnWidth = columnWidth.getFirst();
        var valuesColumnWidth = columnWidth.getSecond();
        worksheet_2.width(0, parameterNamesColumnWidth);
        worksheet_2.width(1, valuesColumnWidth);
        worksheet_2.range(0, 0, 0, 1).style().wrapText(true).set();
        worksheet_2.value(0, 0, SEARCH_PARAMETERS_FIRST_COLUMN_NAME);
        worksheet_2.value(0, 1, SEARCH_PARAMETERS_SECOND_COLUMN_NAME);
    }

    private Pair<Integer, Integer> calculateMaxColumnWidth(ParamsDto params) {
        Map<String, List<String>> parameterNames = defineParameterNames(params);
        Integer parameterNameColumnWidth = parameterNames.keySet().stream()
            .map(String::length)
            .max(Comparator.comparing(Integer::valueOf))
            .orElse(25);

        Integer valuesColumnWidth = parameterNames.entrySet().stream()
            .flatMap(e -> e.getValue().stream())
            .map(String::length)
            .reduce(0, (l, r) -> l > r ? l : r);
        return Pair.of(parameterNameColumnWidth, valuesColumnWidth);
    }

    private void fillParametersTableData(Worksheet worksheet_2, ParamsDto params) {
        fillParameterNames(worksheet_2, params);
        fillParameterValues(worksheet_2, params);
    }

    private void fillParameterNames(Worksheet worksheet_2, ParamsDto params) {
        int numberOfColumn = 0;
        List<String> parameterNames = defineParameterNames(params).keySet().stream().toList();
        int rowAmount = parameterNames.size();
        worksheet_2.range(1, numberOfColumn, rowAmount, 1).style().fillColor("FF8800").wrapText(true).set();
        for (int row = 1; row <= rowAmount; row++) {
            int parameterNameIndex = row - 1;
            worksheet_2.value(row, numberOfColumn, parameterNames.get(parameterNameIndex));
        }
    }

    private void fillParameterValues(Worksheet worksheet_2, ParamsDto params) {
        int numberOfColumn = 1;
        Map<String, List<String>> parameterNames = defineParameterNames(params);
        List<String> values = parameterNames.values().stream()
            .map(list -> String.join("\n", list))
            .toList();

        for (int row = 1; row <= values.size(); row++) {
            int valueIndex = row - 1;
            worksheet_2.value(row, numberOfColumn, values.get(valueIndex));
        }
//        worksheet_2.hyperlink(4, 1, new HyperLink("https://github.com/dhatim/fastexcel", "Baidu"));
    }


    //TODO: define this and other methows inside a wrapper over ParamsDto
    private int defineRowAmount(@NonNull ParamsDto params) {
        int amount = 0;
        List<String> sampleNames = params.getSampleNames();
        List<String> projectNames = params.getProjectNames();
        List<String> processTemplateNames = params.getProcessTemplateNames();
        ConceptType conceptType = params.getConceptType();
        List<String> conceptNames = params.getConceptNames();
        //TODO implement logic
        return 5;
    }

    private Map<String, List<String>> defineParameterNames(@NonNull ParamsDto params) {
        var parameterNames = new LinkedHashMap<String, List<String>>();

        var sampleNames = params.getSampleNames();
        var projectNames = params.getProjectNames();
        var processTemplateNames = params.getProcessTemplateNames();
        var conceptType = params.getConceptType();
        var conceptNames = params.getConceptNames();

        if (!CollectionUtils.isEmpty(sampleNames)) {
            parameterNames.put(SearchParameterName.SAMPLE_NAME.getCode(), sampleNames);
        }
        if (!CollectionUtils.isEmpty(projectNames)) {
            parameterNames.put(SearchParameterName.PROJECT_NAME.getCode(), projectNames);
        }
        if (!CollectionUtils.isEmpty(processTemplateNames)) {
            parameterNames.put(SearchParameterName.PROCESS_TEMPLATE.getCode(), processTemplateNames);
        }
        if (Objects.nonNull(conceptType)){
            parameterNames.put(SearchParameterName.CONCEPT_TYPE.getCode(), List.of(conceptType.name()));
        }
        if (!CollectionUtils.isEmpty(conceptNames)) {
            parameterNames.put(SearchParameterName.CONCEPT_NAME.getCode(), conceptNames);
        }
        return parameterNames;
    }


}
