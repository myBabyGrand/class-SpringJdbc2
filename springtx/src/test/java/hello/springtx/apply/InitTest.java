package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;

@SpringBootTest
public class InitTest {

    @Autowired
    Hello hello;

    @Test
    void go(){
        //직접 호출이 아니라 초기화 시점의 호출을 테스트하는 것이므로 호출 코드 쓰면 안됨
    }

    @TestConfiguration
    static class InitTxTestConfig{
        @Bean
        Hello hello(){
            return new Hello();
        }

    }

    @Slf4j
    static class Hello{

        @PostConstruct
        @Transactional
        public void initV1(){
            boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("hello init @PostConstruct tx active = {}",actualTransactionActive);
        }

        @EventListener(value = ApplicationReadyEvent.class)
        @Transactional
        public void initV2(){
            boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("hello init ApplicationReadyEvent tx active = {}",actualTransactionActive);
        }

    }
}
