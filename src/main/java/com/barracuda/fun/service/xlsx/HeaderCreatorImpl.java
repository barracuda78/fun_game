package com.barracuda.fun.service.xlsx;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class HeaderCreatorImpl {

    public static final String APPLICATION_SPREADSHEET = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static final String X_FILENAME = "X-filename";

    public static final String ATTACHMENT = "attachment";

    public HttpHeaders createExcelHeader(String fileName, ByteArrayResource content) {
        return createHeader(APPLICATION_SPREADSHEET, fileName, content);
    }

    private HttpHeaders createHeader(String contentType, String fileName, ByteArrayResource content) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, contentType);
        httpHeaders.set(X_FILENAME, fileName);
        httpHeaders.setContentDisposition(ContentDisposition
            .builder(ATTACHMENT)
            .filename(fileName, StandardCharsets.UTF_8)
            .build());
        httpHeaders.setContentLength(content.contentLength());
        return httpHeaders;
    }

}
