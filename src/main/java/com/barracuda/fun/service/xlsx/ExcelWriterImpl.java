package com.barracuda.fun.service.xlsx;

import static com.barracuda.fun.service.xlsx.file.FilenameGeneratorImpl.EXTENSION;

import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.data.ParamsDto;
import com.barracuda.fun.service.xlsx.exception.ExcelTemporaryFileWriterException;
import com.barracuda.fun.service.xlsx.file.FilenameGenerator;
import com.barracuda.fun.service.xlsx.processors.SearchParametersWorksheetProcessor;
import com.barracuda.fun.service.xlsx.processors.SearchResultsWorksheetProcessor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

        public void write(Path tempFilePath, List<SampleDto> sampleDtoList, ParamsDto params) {
        //TODO: if sampleDTOList is empty -> return WorkBook with two WorkSheets.

        try (
            OutputStream os = Files.newOutputStream(tempFilePath); //, StandardOpenOption.DELETE_ON_CLOSE
            Workbook workbook = new Workbook(os, "Lumos", "1.0")) //TODO: check these arguments
        {
            CompletableFuture<Void> searchResultFuture = CompletableFuture.runAsync(() ->
                searchResultsWorksheetProcessor.process(workbook, sampleDtoList));
            CompletableFuture<Void> searchParamFuture = CompletableFuture.runAsync(() ->
                searchParametersWorksheetProcessor.process(workbook, params));
            CompletableFuture.allOf(searchResultFuture, searchParamFuture).join();
        } catch (IOException e) {
            throw new ExcelTemporaryFileWriterException(
                "Can not write temporary excel file to the specified location: " + tempFilePath);
        }
    }


//    public byte[] write(List<SampleDto> sampleDtoList, ParamsDto params) {
//        //TODO: if sampleDTOList is empty -> return WorkBook with two WorkSheets.
//        byte[] content = null;
//
//            try (
//                OutputStream os = new ByteArrayOutputStream(); //, StandardOpenOption.DELETE_ON_CLOSE
//                Workbook workbook = new Workbook(os, "Lumos", "1.0")) //TODO: check these arguments
//            {
////            CompletableFuture<Void> searchResultFuture = CompletableFuture.runAsync(() ->
//                searchResultsWorksheetProcessor.process(workbook, sampleDtoList);
////            CompletableFuture<Void> searchParamFuture = CompletableFuture.runAsync(() ->
//                searchParametersWorksheetProcessor.process(workbook, params);
//                Thread.sleep(1000);
////            CompletableFuture.allOf(searchResultFuture, searchParamFuture).join();
////                os.close();
//                content =  ((ByteArrayOutputStream) os).toByteArray();
//            } catch (IOException | InterruptedException e) {
//                throw new ExcelTemporaryFileWriterException(
//                    "Can not write temporary excel file to the specified location: ");
//            }
//
//        return content;
//    }



//    public byte[] write(List<SampleDto> sampleDtoList, ParamsDto params) {
//        //TODO: if sampleDTOList is empty -> return WorkBook with two WorkSheets.
//        byte[] fileContent = null;
//        Path tempFilePath = null;
//        try {
////            tempFilePath = Files.createTempFile(fileNameGenerator.generateWithoutExtension(), EXTENSION);
//            tempFilePath = Files.createFile(Path.of("C:\\Users\\Andrei_Ruzaev\\AppData\\Local\\Temp" + fileNameGenerator.generate()));
//
//        try (
//            OutputStream os = Files.newOutputStream(tempFilePath); //, StandardOpenOption.DELETE_ON_CLOSE
//            Workbook workbook = new Workbook(os, "Lumos", "1.0")) //TODO: check these arguments
//        {
////            CompletableFuture<Void> searchResultFuture = CompletableFuture.runAsync(() ->
//                searchResultsWorksheetProcessor.process(workbook, sampleDtoList);
////            CompletableFuture<Void> searchParamFuture = CompletableFuture.runAsync(() ->
//                searchParametersWorksheetProcessor.process(workbook, params);
//                Thread.sleep(1000);
////            CompletableFuture.allOf(searchResultFuture, searchParamFuture).join();
//            fileContent = Files.readAllBytes(tempFilePath);
//        } catch (IOException | InterruptedException e) {
//            throw new ExcelTemporaryFileWriterException(
//                "Can not write temporary excel file to the specified location: " + tempFilePath);
//        }
//        } catch (IOException e) {
//            throw new ExcelTemporaryFileWriterException(
//                "Can not create temporary file in the specified location");
//        }
//
//        return fileContent;
//    }

}
