package by.bytechs.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Romanovich Andrei 08.10.2020 - 20:56
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionAmountDiagramDto implements Serializable, Comparable<TransactionAmountDiagramDto> {
    @JsonProperty("transactionAmountMap")
    private Map<String, Double> transactionAmountMap;

    @JsonProperty("eventTime")
    private Date eventTime;

    public TransactionAmountDiagramDto() {
    }

    public TransactionAmountDiagramDto(Map<String, Double> transactionAmountMap, Date eventTime) {
        this.transactionAmountMap = transactionAmountMap;
        this.eventTime = eventTime;
    }

    public Map<String, Double> getTransactionAmountMap() {
        return transactionAmountMap;
    }

    public void setTransactionAmountMap(Map<String, Double> transactionAmountMap) {
        this.transactionAmountMap = transactionAmountMap;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public int compareTo(TransactionAmountDiagramDto o) {
        if (!this.getEventTime().equals(o.getEventTime())) {
            return this.getEventTime().compareTo(o.getEventTime());
        }
        return 0;
    }
}
