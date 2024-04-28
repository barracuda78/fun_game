package com.barracuda.fun.service.xlsx;

import com.barracuda.fun.service.data.ParamsDto;
import com.barracuda.fun.service.data.SampleDto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl {

    private final HeaderCreatorImpl headerCreator;

    private final ExcelWriterImpl excelWriter;

    public FileResponse getFileResponse(List<SampleDto> sampleDtoList,  ParamsDto paramsDto) {
        String id = "some_file_name";
        String filename = String.format("%s.xlsx", id);
        byte[] array = getByteArrayFromSearchResult(sampleDtoList, paramsDto);
        ByteArrayResource content = new ByteArrayResource(array);
        HttpHeaders header = headerCreator.createExcelHeader(filename, content);
        return new FileResponse(header, content);
    }

    public byte[] getByteArrayFromSearchResult(List<SampleDto> sampleDtoList,  ParamsDto paramsDto) {
        String filePathString = "C:\\epam\\projects\\ALL_LEARNING_PROJECTS\\fun\\fun\\src\\main\\resources\\xls\\object_collection_output.xlsx";

        excelWriter.write(filePathString, sampleDtoList, paramsDto);
        Path path = Paths.get(filePathString);
        byte[] fileContent = null;
        try {
            fileContent = Files.readAllBytes(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

}
