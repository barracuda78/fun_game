package com.barracuda.fun.api.controller;


import com.barracuda.fun.service.data.ParamsDto;
import com.barracuda.fun.service.data.SampleDto;
import com.barracuda.fun.service.data.SampleFactory;
import com.barracuda.fun.service.xlsx.FileResponse;
import com.barracuda.fun.service.xlsx.ResultServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class XlsxSpringExportController {

    private final ResultServiceImpl resultService;

    @GetMapping(path = "v1/new-export")
    public ResponseEntity<Resource> exportToXls() {
        List<SampleDto> sampleDtoList = List.of(
            SampleFactory.createSomeDTOBuilder(1).build(),
            SampleFactory.createSomeDTOBuilder(2).build()
        );

        ParamsDto paramsDto = SampleFactory.createParams().build();

        FileResponse fileResponse = resultService.getFileResponse(sampleDtoList, paramsDto);
        return new ResponseEntity<>(fileResponse.getInputStreamSource(), fileResponse.getHttpHeaders(), HttpStatus.OK);
    }


}
