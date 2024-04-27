package com.barracuda.fun.service.xlsx.first_approach;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExportServiceImpl implements ExportService {

    public byte[] export() {
        writeExcel("C:\\epam\\projects\\ALL_LEARNING_PROJECTS\\fun\\fun\\src\\main\\resources\\xls\\object_collection_output.xlsx");
        Path path = Paths.get("C:\\epam\\projects\\ALL_LEARNING_PROJECTS\\fun\\fun\\src\\main\\resources\\xls\\object_collection_output.xlsx");
        byte[] fileContent = null;
        try {
            fileContent = Files.readAllBytes(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
//        return null;
    }

    private void writeExcel(String fileLocation) {
//        File currDir = new File(".");
//        String path = currDir.getAbsolutePath();
//        String fileLocation = path.substring(0, path.length() - 1) + "fastexcel.xlsx";

        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation)); Workbook wb = new Workbook(os, "MyApplication", "1.0")) {
            Worksheet ws = wb.newWorksheet("Sheet 1");
            ws.width(0, 25);
            ws.width(1, 15);

            ws.range(0, 0, 0, 1).style().fontName("Arial").fontSize(16).bold().fillColor("3366FF").set();
            ws.value(0, 0, "Name");
            ws.value(0, 1, "Age");

            ws.range(2, 0, 2, 1).style().wrapText(true).set();
            ws.value(2, 0, "Andrei Ruzaev");
            ws.value(2, 1, 45L);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
