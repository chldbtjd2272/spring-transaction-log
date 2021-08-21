package spring.transaction.core;

import org.slf4j.MDC;

import java.util.Optional;
import java.util.function.Supplier;

public class TransactionLogManager<T extends TransactionLog> {
    private static final String MDC_TRACE_ID = "TID";

    private final ThreadLocal<T> transactionLogStore = new ThreadLocal<>();
    private final Supplier<T> transactionLogFactory;

    public TransactionLogManager(Supplier<T> transactionLogFactory) {
        this.transactionLogFactory = transactionLogFactory;
    }

    public T startLog() {
        T transactionLog = transactionLogStore.get();
        if (transactionLog == null) {
            transactionLog = transactionLogFactory.get();
            MDC.put(MDC_TRACE_ID, transactionLog.getTransactionId());
            this.transactionLogStore.set(transactionLog);
        }
        return transactionLog;
    }

    public T endLog() {
        T transactionLog = transactionLogStore.get();
        if (transactionLog == null) {
            throw new IllegalStateException("transaction log start not yet");
        }
        transactionLogStore.remove();
        MDC.remove(MDC_TRACE_ID);
        transactionLog.end();
        return transactionLog;
    }

    public Optional<T> transactionLog() {
        T transactionLog = transactionLogStore.get();
        return Optional.ofNullable(transactionLog);
    }
}
