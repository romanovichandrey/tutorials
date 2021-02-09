package by.bytechs.service;

import by.bytechs.excel.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by Katsuba_VV on 08.04.2015.
 */
@Service
public class SaveCSVDialog {
    private final ExcelService excelService;

    @Autowired
    public SaveCSVDialog(ExcelService excelService) {
        this.excelService = excelService;
    }

    public void saveFileXlsX(List<String[]> records, boolean statusStale) {
        File fileWithExtension = new File("/tmp/xlsx/CashWithdrawal.xlsx");
        try {
            excelService.writeXLSX(fileWithExtension, records, statusStale,
                    fileWithExtension.getName().substring(0, fileWithExtension.getName().indexOf('.')));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
