package com.barracuda.fun.service.xlsx;

import static com.barracuda.fun.service.xlsx.file.FilenameGeneratorImpl.EXTENSION;

import com.barracuda.fun.service.data.ParamsDto;
import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.xlsx.file.FilenameGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private final FilenameGenerator fileNameGenerator;

    public FileResponse getFileResponse(List<SampleDto> sampleDtoList,  ParamsDto paramsDto) {
        String filename = fileNameGenerator.generate();
        byte[] array = getByteArrayFromSearchResult(sampleDtoList, paramsDto, filename);
        ByteArrayResource content = new ByteArrayResource(array);
        HttpHeaders header = headerCreator.createExcelHeader(filename, content);
        return new FileResponse(header, content);
    }

    private byte[] getByteArrayFromSearchResult(List<SampleDto> sampleDtoList,  ParamsDto paramsDto, String filename) {
        byte[] fileContent = null;
        try {
            Path tempFilePath = Files.createTempFile(fileNameGenerator.generateWithoutExtension(), EXTENSION);
            excelWriter.write(tempFilePath, sampleDtoList, paramsDto);
            fileContent = Files.readAllBytes(tempFilePath);
            tempFilePath.toFile().deleteOnExit();
        }
        catch (IOException e) {
            e.printStackTrace(); //TODO: custom exception
        }
        return fileContent;
    }

}
