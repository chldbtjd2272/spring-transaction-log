package spring.transaction.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionLogTest {

    @Test
    @DisplayName("트랜잭션 로그는 시작과 끝난 시간 구간을 알 수 있다")
    void name() throws InterruptedException {
        //given
        int executeTime = 500;
        TransactionLog transactionLog = new TransactionLog();

        //when
        Thread.sleep(executeTime);
        transactionLog.end();
        //then
        assertThat(transactionLog.getExecuteTime() > executeTime).isTrue();
    }
}
