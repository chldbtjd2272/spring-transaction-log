package spring.transaction.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionLogManagerTest {

    @Test
    @DisplayName("트랜잭션 로그는 스레드별로 관리된다")
    void name() throws InterruptedException {
        //given
        TransactionLogManager<TransactionLog> manager = new TransactionLogManager<>(TransactionLog::new);
        int logCount = 10;
        Set<String> transactionCaptures = new HashSet<>();
        CountDownLatch latch = new CountDownLatch(logCount);
        Runnable logMaker = () -> {
            TransactionLog log = manager.startLog();
            transactionCaptures.add(log.getTransactionId());
            latch.countDown();
            log.end();
        };

        //when
        for (int i = 0; i < logCount; i++) {
            new Thread(logMaker).start();
        }
        latch.await();

        //then
        assertThat(transactionCaptures.size()).isEqualTo(logCount);
    }


    @Test
    @DisplayName("트랜잭션 로그는 같은 스레드에서는 동일 트랜잭션로그가 반환된다")
    void name2() {
        //given
        TransactionLogManager<TransactionLog> manager = new TransactionLogManager<>(TransactionLog::new);
        //when
        TransactionLog transactionLog = manager.startLog();
        TransactionLog transactionLog2 = manager.startLog();
        //then
        assertThat(transactionLog.getTransactionId()).isEqualTo(transactionLog2.getTransactionId());
    }

    @Test
    @DisplayName("트랜잭션 로그를 끝내면 다음 로그 생성이 가능하다")
    void name3() {
        //given
        TransactionLogManager<TransactionLog> manager = new TransactionLogManager<>(TransactionLog::new);
        TransactionLog transactionLog = manager.startLog();
        manager.endLog();

        //when
        TransactionLog transactionLog2 = manager.startLog();

        //then
        assertThat(transactionLog.getTransactionId()).isNotEqualTo(transactionLog2.getTransactionId());
    }
}