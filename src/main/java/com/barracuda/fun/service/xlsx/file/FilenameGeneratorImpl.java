package com.barracuda.fun.service.xlsx.file;

import com.barracuda.fun.service.xlsx.DateTimeFormatter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilenameGeneratorImpl implements FilenameGenerator {

    public static final String FILE_NAME_BASE = "Lumos_SearchResults_%s";

    public static final String EXTENSION = ".xlsx";

    private final DateTimeFormatter formatter;

    @Override
    public String generate() {
        var dateTime = formatter.formatDateTime(LocalDateTime.now());
        return String.format(FILE_NAME_BASE, dateTime).concat(EXTENSION);
    }

    @Override
    public String generateWithoutExtension() {
        var dateTime = formatter.formatDateTime(LocalDateTime.now());
        return String.format(FILE_NAME_BASE, dateTime);
    }

}
