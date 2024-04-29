package com.barracuda.fun.service.xlsx.processors;

import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.CENTER_HORIZONTAL_ALIGNMENT;
import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.NEW_LINE_DELIMITER;
import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.SEARCH_PARAMETERS_FIRST_COLUMN_NAME;
import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.SEARCH_PARAMETERS_SECOND_COLUMN_NAME;
import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.SEARCH_PARAMETERS_WORKSHEET_NAME;

import com.barracuda.fun.service.data.ParamsDto;
import com.barracuda.fun.service.xlsx.SearchParameterName;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.dhatim.fastexcel.BorderStyle;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class SearchParametersWorksheetProcessorImpl implements SearchParametersWorksheetProcessor {

    @Override
    public void process(@NonNull Workbook workbook, @NonNull ParamsDto params) {
        Worksheet worksheet_2 = workbook.newWorksheet(SEARCH_PARAMETERS_WORKSHEET_NAME);
        createParametersTableHeader(worksheet_2, params);
        fillParametersTableData(worksheet_2, params);
    }

    private void createParametersTableHeader(Worksheet worksheet_2, ParamsDto params) {
        var columnWidth = calculateMaxColumnWidth(params);
        var parameterNamesColumnWidth = columnWidth.getFirst();
        var valuesColumnWidth = columnWidth.getSecond();
        worksheet_2.width(0, parameterNamesColumnWidth);
        worksheet_2.width(1, valuesColumnWidth);
        worksheet_2.range(0, 0, 0, 1).style()
            .horizontalAlignment(CENTER_HORIZONTAL_ALIGNMENT)
            .wrapText(true)
            .borderStyle(BorderStyle.THIN)
            .bold()
            .set();
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
        worksheet_2.range(1, numberOfColumn, rowAmount, 1).style()
//            .fillColor("FF8800") //TODO: remove
            .wrapText(true)
            .borderStyle(BorderStyle.THIN)
            .set();
        for (int row = 1; row <= rowAmount; row++) {
            int parameterNameIndex = row - 1;
            worksheet_2.value(row, numberOfColumn, parameterNames.get(parameterNameIndex));
        }
    }

    private void fillParameterValues(Worksheet worksheet_2, ParamsDto params) {
        int numberOfColumn = 1;
        Map<String, List<String>> parameterNames = defineParameterNames(params);
        List<String> values = parameterNames.values().stream()
            .map(list -> String.join(NEW_LINE_DELIMITER, list))
            .toList();

        for (int row = 1; row <= values.size(); row++) {
            int valueIndex = row - 1;
            worksheet_2.value(row, numberOfColumn, values.get(valueIndex));
        }
    }


    //TODO: define this and other methows inside a wrapper over ParamsDto

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
