package com.barracuda.fun.service.xlsx.processors;

import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.CENTER_HORIZONTAL_ALIGNMENT;
import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.NEW_LINE_DELIMITER;
import static com.barracuda.fun.service.xlsx.ExcelWriterImpl.SEARCH_RESULTS_WORKSHEET_NAME;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.ISN;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.LATIN_NAME;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.LINKED_CONCEPTS;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.PHYSICAL_FORM;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.PRODUCTION_DATE;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.PROJECT_CODES;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.SAMPLE_NAME;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.SOURCE_PROCESS;
import static com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName.VESSEL;

import com.barracuda.fun.service.data.DictionaryValueDto;
import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.data.SampleVesselDto;
import com.barracuda.fun.service.xlsx.DateTimeFormatter;
import com.barracuda.fun.service.xlsx.SearchResultColumnHeaderName;
import com.barracuda.fun.service.xlsx.resolver.column_width_resolver.ColumnWidthResolver;
import com.barracuda.fun.service.xlsx.resolver.column_width_resolver.ColumnWidthResolverRegistry;
import com.barracuda.fun.service.xlsx.resolver.hyperlink_resolver.HyperLinkResolverRegistry;
import com.barracuda.fun.service.xlsx.resolver.hyperlink_resolver.HyperlinkResolver;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.dhatim.fastexcel.HyperLink;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class SearchResultsWorksheetProcessorImpl implements SearchResultsWorksheetProcessor {

    private final ColumnWidthResolverRegistry columnWidthResolverRegistry;

    private final HyperLinkResolverRegistry hyperLinkResolverRegistry;

    private final DateTimeFormatter formatter;

    @Override
    public void process(@NonNull Workbook workbook, @NonNull List<SampleDto> sampleDtoList) {
        Worksheet worksheet_1 = workbook.newWorksheet(SEARCH_RESULTS_WORKSHEET_NAME);
        createSearchResultTableHeader(worksheet_1, sampleDtoList);
        fillSearchResultTableHeader(worksheet_1);
        createSearchResultTableContent(worksheet_1, sampleDtoList);
//            //worksheet_1 content style
//            worksheet_1.range(2, 0, 2, 1).style().wrapText(true).set();
    }

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
        ColumnWidthResolver resolver = columnWidthResolverRegistry.getResolver(e);
        return resolver.resolve(e, sampleDtoList);
    }

    public void fillSearchResultTableHeader(Worksheet worksheet_1) {
        int headerRowNumber = 0;
        Arrays.stream(SearchResultColumnHeaderName.values())
            .forEach(e -> worksheet_1.value(headerRowNumber, e.ordinal(), e.getCode()));
    }

    public  void createSearchResultTableContent(Worksheet worksheet_1, List<SampleDto> sampleDtoList) {
        //TODO: style -> to separate logic!
        //TODO: align vertically!
        worksheet_1.range(1, 0, sampleDtoList.size(), SearchResultColumnHeaderName.values().length)
            .style()
            .wrapText(true)
            .set();
        for (int i = 0; i < sampleDtoList.size(); i++) {
            writeSingleRow(worksheet_1, sampleDtoList.get(i), 1 + i);
        }
    }

    private void writeSingleRow(Worksheet worksheet_1, SampleDto sampleDto, int rowNumber) {
        HyperlinkResolver sampleNameHyperlinkResolver = hyperLinkResolverRegistry.getRegistry(SAMPLE_NAME);
        worksheet_1.hyperlink(
            rowNumber,
            SAMPLE_NAME.getColumnNumber(),
            new HyperLink(sampleNameHyperlinkResolver.createUrl(sampleDto), sampleDto.getName())
        );


        if (Objects.nonNull(sampleDto.getProcess())) {
            HyperlinkResolver processHyperlinkResolver = hyperLinkResolverRegistry.getRegistry(SOURCE_PROCESS);
            worksheet_1.hyperlink(
                rowNumber,
                SOURCE_PROCESS.getColumnNumber(),
                new HyperLink(processHyperlinkResolver.createUrl(sampleDto), sampleDto.getProcess().getName()));
        }

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
        worksheet_1.value(rowNumber, PRODUCTION_DATE.getColumnNumber(), formatter.formatDate(sampleDto.getProductionDate()));
        worksheet_1.value(rowNumber, VESSEL.getColumnNumber(), Objects.isNull(sampleDto.getVessels())
            ? ""
            : sampleDto.getVessels().stream()
                .map(SampleVesselDto::getBarcode)
                .collect(Collectors.joining(NEW_LINE_DELIMITER)));


        if (! CollectionUtils.isEmpty(sampleDto.getLinkedConcepts())) {
            HyperlinkResolver linkedConceptsHyperlinkResolver = hyperLinkResolverRegistry.getRegistry(LINKED_CONCEPTS);
            worksheet_1.hyperlink(
                rowNumber,
                LINKED_CONCEPTS.getColumnNumber(),
                new HyperLink(linkedConceptsHyperlinkResolver.createUrl(sampleDto), sampleDto.getLinkedConcepts().stream()
                    .map(lc -> lc.getName() + " (" + lc.getConceptType().name() + ")")//TODO: check in the real app that enum has a good string representation
                    .collect(Collectors.joining(NEW_LINE_DELIMITER)))
            );
        }
    }

}
