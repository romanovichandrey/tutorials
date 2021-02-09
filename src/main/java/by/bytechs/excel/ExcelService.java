package by.bytechs.excel;

import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This interface intended for generic xls file.
 *
 * @author Romanovich Andrei
 */
public interface ExcelService {

    void write(File file, List<String[]> reportList)
            throws IOException, WriteException;

    void closeWorkBook();

    void writeXLSX(File file, List<String[]> reportList, boolean statusStyle, String name) throws IOException, WriteException;

}
