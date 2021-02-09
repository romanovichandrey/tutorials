package by.bytechs.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.TreeSet;

/**
 * @author Romanovich Andrei 01.09.2020 - 16:14
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiagramDto implements Serializable {
    @JsonProperty("customerServiceDiagram")
    private TreeSet<OperationsDiagramDto> customerServiceDiagram;

    @JsonProperty("fallbackDiagram")
    private TreeSet<OperationsDiagramDto> fallbackDiagram;

    @JsonProperty("cashWithdrawalSuccessDiagram")
    private TreeSet<OperationsDiagramDto> cashWithdrawalSuccessDiagram;

    @JsonProperty("transactionAmountDiagram")
    TreeSet<TransactionAmountDiagramDto> transactionAmountDiagram;

    public DiagramDto() {
    }

    public DiagramDto(TreeSet<OperationsDiagramDto> customerServiceDiagram,
                      TreeSet<OperationsDiagramDto> fallbackDiagram,
                      TreeSet<OperationsDiagramDto> cashWithdrawalSuccessDiagram) {
        this.customerServiceDiagram = customerServiceDiagram;
        this.fallbackDiagram = fallbackDiagram;
        this.cashWithdrawalSuccessDiagram = cashWithdrawalSuccessDiagram;
    }

    public DiagramDto(TreeSet<TransactionAmountDiagramDto> transactionAmountDiagram) {
        this.transactionAmountDiagram = transactionAmountDiagram;
    }

    public TreeSet<OperationsDiagramDto> getCustomerServiceDiagram() {
        return customerServiceDiagram;
    }

    public void setCustomerServiceDiagram(TreeSet<OperationsDiagramDto> customerServiceDiagram) {
        this.customerServiceDiagram = customerServiceDiagram;
    }

    public TreeSet<OperationsDiagramDto> getFallbackDiagram() {
        return fallbackDiagram;
    }

    public void setFallbackDiagram(TreeSet<OperationsDiagramDto> fallbackDiagram) {
        this.fallbackDiagram = fallbackDiagram;
    }

    public TreeSet<OperationsDiagramDto> getCashWithdrawalSuccessDiagram() {
        return cashWithdrawalSuccessDiagram;
    }

    public void setCashWithdrawalSuccessDiagram(TreeSet<OperationsDiagramDto> cashWithdrawalSuccessDiagram) {
        this.cashWithdrawalSuccessDiagram = cashWithdrawalSuccessDiagram;
    }

    public TreeSet<TransactionAmountDiagramDto> getTransactionAmountDiagram() {
        return transactionAmountDiagram;
    }

    public void setTransactionAmountDiagram(TreeSet<TransactionAmountDiagramDto> transactionAmountDiagram) {
        this.transactionAmountDiagram = transactionAmountDiagram;
    }
}
