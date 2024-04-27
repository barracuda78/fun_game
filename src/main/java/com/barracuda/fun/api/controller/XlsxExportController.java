package com.barracuda.fun.api.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class XlsxExportController {

//    private final ExportService exportService;

    @GetMapping(path = "v1/export")
    public HttpEntity<byte[]> createNewFun(HttpServletResponse response) {
//        byte[] excelContent = exportService.export();
        byte[] excelContent = null;

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=my_file.xls");
        header.setContentLength(excelContent.length);

        return new HttpEntity<>(excelContent, header);
//        return new HttpEntity<>("JOB IS DONE!");
    }


}
