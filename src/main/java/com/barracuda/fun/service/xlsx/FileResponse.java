package com.barracuda.fun.service.xlsx;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.AbstractResource;
import org.springframework.http.HttpHeaders;

@Data
@AllArgsConstructor
public class FileResponse {

    private final HttpHeaders httpHeaders;

    private final AbstractResource inputStreamSource;

}
