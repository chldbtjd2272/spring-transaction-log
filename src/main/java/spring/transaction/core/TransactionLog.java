package spring.transaction.core;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class TransactionLog {
    private String transactionId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long executeTime;

    public TransactionLog() {
        this.transactionId = UUID.randomUUID().toString().replace("-", "");
        ;
        this.startDateTime = LocalDateTime.now();
    }

    void end() {
        this.endDateTime = LocalDateTime.now();
        this.executeTime = convertTimeMillis(endDateTime) - convertTimeMillis(startDateTime);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    private long convertTimeMillis(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
