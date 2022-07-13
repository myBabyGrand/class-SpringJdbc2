package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LogRepository logRepository;

    /**
     * memberService : @Transactional - Off
     * memberRepository : @Transactional - On
     * logRepository : @Transactional - On
     * */
    @Test
    void outerTxOff_success(){
        //given
        String username = "outerTxOff_success";

        //when
        memberService.joinV1(username);

        //then : 모든 데이터가 정상 저장
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());

    }

    /**
     * memberService : @Transactional - Off
     * memberRepository : @Transactional - On
     * logRepository : @Transactional - On Exception
     * */
    @Test
    void outerTxOff_fail(){
        //given
        String username = "로그예외_outerTxOff_success";

        //when
        assertThatThrownBy(()-> memberService.joinV1(username)).isInstanceOf(RuntimeException.class);

        //then : 모든 데이터가 정상 저장
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isEmpty());

    }

}