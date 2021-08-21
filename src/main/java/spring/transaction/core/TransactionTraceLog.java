package spring.transaction.core;

public class TransactionTraceLog {

    private static final int ROOT_LOG_DEPTH = 0;

    private int logDepth;
    private String transactionId;

    public TransactionTraceLog(String transactionId) {
        this.logDepth = ROOT_LOG_DEPTH;
        this.transactionId = transactionId;
    }


    private StringBuilder generateLogBuilder(int logDepth, String prefixLog, String suffixLog) {
        StringBuilder logMessage = new StringBuilder();
        if (logDepth == 0) {
            logMessage.append(prefixLog);
        } else {
            for (int i = 1; i < logDepth; i++) {
                logMessage.append("|   ");
            }
            logMessage.append(suffixLog);
        }
        return logMessage;
    }

}
