package by.bytechs.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Romanovich Andrei 01.09.2020 - 16:04
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationsDiagramDto implements Serializable, Comparable<OperationsDiagramDto> {
    @JsonProperty("xAxisValue")
    private Date xAxisValue;
    @JsonProperty("yAxisValue")
    private long yAxisValue;

    public OperationsDiagramDto() {
    }

    public OperationsDiagramDto(Date xAxisValue, long yAxisValue) {
        this.xAxisValue = xAxisValue;
        this.yAxisValue = yAxisValue;
    }

    public Date getxAxisValue() {
        return xAxisValue;
    }

    public void setxAxisValue(Date xAxisValue) {
        this.xAxisValue = xAxisValue;
    }

    public long getyAxisValue() {
        return yAxisValue;
    }

    public void setyAxisValue(long yAxisValue) {
        this.yAxisValue = yAxisValue;
    }

    @Override
    public int compareTo(OperationsDiagramDto o) {
        if (!this.getxAxisValue().equals(o.getxAxisValue())) {
            return this.getxAxisValue().compareTo(o.getxAxisValue());
        }
        return 0;
    }
}
