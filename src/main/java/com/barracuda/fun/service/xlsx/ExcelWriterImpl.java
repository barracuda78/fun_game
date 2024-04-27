package com.barracuda.fun.service.xlsx;

import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.ISN;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.LATIN_NAME;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.LINKED_CONCEPTS;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.PHYSICAL_FORM;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.PRODUCTION_DATE;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.PROJECT_CODES;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.SAMPLE_NAME;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.SOURCE_PROCESS;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.VESSEL;

import com.barracuda.fun.service.data.ConceptType;
import com.barracuda.fun.service.data.DictionaryValueDto;
import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.data.ParamsDto;
import com.barracuda.fun.service.data.SampleVesselDto;
import com.barracuda.fun.service.xlsx.processors.TableHeaderProcessor;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
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
    public static final String NEW_LINE_DELIMITER = "\n";
    public static final String CENTER_HORIZONTAL_ALIGNMENT = "center";
    public static int CELL_ADDITIONAL_WIDTH = 6;

    private final TableHeaderProcessor tableHeaderProcessor;


    //TODO: here use sampleDTOList to write it into xslx file

    //TODO: Default file name is ‘Lumos_SearchResults_<date-time>.xlsx’
    public void writeExcel(String fileLocation, List<SampleDto> sampleDtoList, ParamsDto params) {
        //TODO: if sampleDTOList is empty -> return WorkBook with two WorkSheets.

        //TODO: use Multithreaded generation for both sheets;

        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation)); Workbook workbook = new Workbook(os, "MyApplication", "1.0")) {

            Worksheet worksheet_1 = workbook.newWorksheet(SEARCH_RESULTS_WORKSHEET_NAME);
            createSearchResultTableHeader(worksheet_1, sampleDtoList);
            fillSearchResultTableHeader(worksheet_1);
            createSearchResultTableContent(worksheet_1, sampleDtoList);
//            //worksheet_1 content style
//            worksheet_1.range(2, 0, 2, 1).style().wrapText(true).set();

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
    public void createSearchResultTableHeader(Worksheet worksheet_1, List<SampleDto> sampleDtoList) {
        var columnAmount = SearchResultColumnHeaderName.values().length;
        var rightBoarderCoordinate = columnAmount - 1;
        worksheet_1.range(0, 0, 0, rightBoarderCoordinate).style()
            .horizontalAlignment(CENTER_HORIZONTAL_ALIGNMENT)
            .fillColor("FF8800")
            .wrapText(true)
            .set();
        Arrays.stream(SearchResultColumnHeaderName.values())
                .forEach(e -> worksheet_1.width(e.ordinal(), defineSearchResultTableColumnWidth(e, sampleDtoList)));
    }

    private int defineSearchResultTableColumnWidth(SearchResultColumnHeaderName e, List<SampleDto> sampleDtoList) {
        return switch (e) {
            case SAMPLE_NAME -> {int maxValueLength = sampleDtoList.stream()
                .map(s -> s.getName().length())
                .reduce(0, (l,r) -> l > r ? l : r);
                yield maxValueLength > e.getLength() ? maxValueLength + CELL_ADDITIONAL_WIDTH : e.getLength() + CELL_ADDITIONAL_WIDTH;
            }
            case SOURCE_PROCESS -> {int maxValueLength = sampleDtoList.stream()
                .map(s -> s.getProcess().getName().length())//TODO: NPE on getName
                .reduce(0, (l,r) -> l > r ? l : r);
                yield maxValueLength > e.getLength() ? maxValueLength + CELL_ADDITIONAL_WIDTH : e.getLength() + CELL_ADDITIONAL_WIDTH;
            }
            case ISN -> 35; //TODO: implement chain-of-responsibility
            case LATIN_NAME -> 35;
            case PROJECT_CODES -> 35;
            case PHYSICAL_FORM -> 35;
            case PRODUCTION_DATE -> 35;
            case VESSEL -> 35;
            case LINKED_CONCEPTS -> 35;

        };
    }

    public void fillSearchResultTableHeader(Worksheet worksheet_1) {
        int headerRowNumber = 0;
        Arrays.stream(SearchResultColumnHeaderName.values())
            .forEach(e -> worksheet_1.value(headerRowNumber, e.ordinal(), e.getCode()));
    }

    public  void createSearchResultTableContent(Worksheet worksheet_1, List<SampleDto> sampleDtoList) {
        for (int i = 0; i < sampleDtoList.size(); i++) {
            writeSingleRow(worksheet_1, sampleDtoList.get(i), 1 + i);
        }
    }

    public void writeSingleRow(Worksheet worksheet_1, SampleDto sampleDto, int rowNumber) {
        worksheet_1.value(rowNumber, SAMPLE_NAME.getColumnNumber(), sampleDto.getName());
        worksheet_1.value(rowNumber, SOURCE_PROCESS.getColumnNumber(), Objects.isNull(sampleDto.getProcess())
            ? "" //TODO: do not write empty value to the cell
            : sampleDto.getProcess().getName());
        worksheet_1.value(rowNumber, ISN.getColumnNumber(), sampleDto.getIsn());
        worksheet_1.value(rowNumber, LATIN_NAME.getColumnNumber(), Objects.isNull(sampleDto.getLatinNames())
            ? ""
            : String.join(NEW_LINE_DELIMITER, sampleDto.getLatinNames()));
        worksheet_1.value(rowNumber, PROJECT_CODES.getColumnNumber(), Objects.isNull(sampleDto.getProjectCodes())
            ? ""
            : sampleDto.getProjectCodes().stream()
                .map(DictionaryValueDto::getCode)
                .collect(Collectors.joining(NEW_LINE_DELIMITER)));
        worksheet_1.value(rowNumber, PHYSICAL_FORM.getColumnNumber(), Objects.isNull(sampleDto.getPhysicalForm())
            ? ""
            : sampleDto.getPhysicalForm().getValue());
        worksheet_1.value(rowNumber, PRODUCTION_DATE.getColumnNumber(), sampleDto.getProductionDate()); //TODO: format date!
        worksheet_1.value(rowNumber, VESSEL.getColumnNumber(), Objects.isNull(sampleDto.getVessels())
            ? ""
            : sampleDto.getVessels().stream()
                .map(SampleVesselDto::getBarcode)
                .collect(Collectors.joining(NEW_LINE_DELIMITER)));
        worksheet_1.value(rowNumber, LINKED_CONCEPTS.getColumnNumber(), Objects.isNull(sampleDto.getLinkedConcepts())
            ? ""
            : sampleDto.getLinkedConcepts().stream()
                .map(lc -> lc.getName() + " (" + lc.getConceptType().name() + ")")//TODO: checko in the real app that enum has a good string representation
                .collect(Collectors.joining(NEW_LINE_DELIMITER))
            );
    }

    //=============================================================================================
    // Methods to handle parameters table (worksheet 2) :
    private void createParametersTableHeader(Worksheet worksheet_2, ParamsDto params) {
        var columnWidth = calculateMaxColumnWidth(params);
        var parameterNamesColumnWidth = columnWidth.getFirst();
        var valuesColumnWidth = columnWidth.getSecond();
        worksheet_2.width(0, parameterNamesColumnWidth);
        worksheet_2.width(1, valuesColumnWidth);
        worksheet_2.range(0, 0, 0, 1).style()
            .horizontalAlignment(CENTER_HORIZONTAL_ALIGNMENT)
            .wrapText(true)
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
            .map(list -> String.join(NEW_LINE_DELIMITER, list))
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
