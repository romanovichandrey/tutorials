package by.bytechs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Romanovich Andrei 27.10.2020 - 9:35
 */
@Service
public class CashWithdrawalServiceImpl implements CashWithdrawalService {
    private final RestTemplate restTemplate;
    private final SaveCSVDialog saveCSVDialog;

    @Autowired
    public CashWithdrawalServiceImpl(RestTemplate restTemplate, SaveCSVDialog saveCSVDialog) {
        this.restTemplate = restTemplate;
        this.saveCSVDialog = saveCSVDialog;
    }

    @Override
    //@PostConstruct
    public void exportToCsv() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS");
        LocalDateTime dateFrom = LocalDateTime.parse("09.09.2020 00:00:00.000", dateTimeFormatter);
        LocalDateTime dateTo = LocalDateTime.parse("27.10.2020 11:00:00.000", dateTimeFormatter);

        LocalDateTime dateEnd = null;
        TreeSet<TransactionAmountDiagramDto> resultSet = new TreeSet<>();
        do {
            dateEnd = dateFrom.plusDays(1);
            if (dateEnd.isAfter(dateTo)) {
                dateEnd = dateTo;
            }
            String ISODate_FROM = dateFrom.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).concat(".000Z");
            String ISODate_TO = dateEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).concat(".000Z");
            DiagramDto diagram = getData(ISODate_FROM, ISODate_TO);
            if (diagram != null && diagram.getTransactionAmountDiagram() != null) {
                resultSet.addAll(diagram.getTransactionAmountDiagram());
            }
            dateFrom = dateEnd;
        } while (!dateEnd.isEqual(dateTo) && !dateEnd.isAfter(dateTo));

        String[] columns = {"Время снятия наличных", "BYN", "USD", "EUR", "RUB"};
        List<String[]> records = reportToRecords(columns, resultSet);
        saveCSVDialog.saveFileXlsX(records, false);


    }

    private DiagramDto getData(String dateFrom, String dateTo) {
        String uri = "http://10.177.54.15:8888/diagram/transaction-amount/";
        HttpHeaders headers = new HttpHeaders();
        headers.add("DATE_FROM", dateFrom);
        headers.add("DATE_TO", dateTo);
        headers.add("Accept", "application/json;charset=utf-8");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<DiagramDto> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, DiagramDto.class);
        return responseEntity.getBody();
    }

    public List<String[]> reportToRecords(String[] columns, TreeSet<TransactionAmountDiagramDto> resultSet) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        List<String[]> recordedData = new ArrayList<>();
        String[] columnNames = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnNames[i] = columns[i];
        }
        recordedData.add(columnNames);
        for (TransactionAmountDiagramDto dto : resultSet) {
            if (dto.getTransactionAmountMap() == null) {
                continue;
            }
            String[] newRecordData = new String[columns.length];
            recordedData.add(newRecordData);
            newRecordData[0] = dto.getEventTime() != null ? dateFormat.format(dto.getEventTime()) : "";
            for (int i = 1; i < columns.length; i++) {
                Double amount = dto.getTransactionAmountMap().get(columns[i]);
                newRecordData[i] = amount != null ? String.valueOf(amount) : "";
            }

        }
        return recordedData;
    }
}
