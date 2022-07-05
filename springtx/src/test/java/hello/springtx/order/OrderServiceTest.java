package hello.springtx.order;

import hello.springtx.NotEnoughMoneyException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void complete() throws NotEnoughMoneyException{
        //given
        Order order = new Order();
        order.setUsername("정상");

        //when
        orderService.order(order);

        //then
        Order findOrder = orderRepository.findById(order.getId()).get();
        assertThat("완료".equals(findOrder.getPayStatus()));
    }
    
    @Test
    void runtimeException(){
        //given
        Order order = new Order();
        order.setUsername("예외");

        //when, then
        assertThatThrownBy(()-> orderService.order(order))
                .isInstanceOf(RuntimeException.class);
        
        Optional<Order> findOrder = orderRepository.findById(order.getId());
        assertThat(findOrder.isEmpty()).isTrue();
    }

    @Test
    void bizException(){
        //given
        Order order = new Order();
        order.setUsername("잔고부족");
        
        //when
        try {
            orderService.order(order);
            fail("잔고 부족 예외가 발생");
        } catch (NotEnoughMoneyException e) {
            log.info(e.getMessage());
            log.info("고객에게 잔고부족을 알리고 계좌입금을 안내하세요");
        }
        
        //then
        Order findOrder = orderRepository.findById(order.getId()).get();
        assertThat("대기".equals(findOrder.getPayStatus()));
    }
    

}