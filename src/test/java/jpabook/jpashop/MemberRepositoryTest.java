package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // 테스트 코드에 이 어노테이션 넣어주면 데이터 알아서 롤백 시켜준다.
    //@Rollback(value = false)
    void testMember(){
        // given
        Member member = new Member();
        //member.setUsername("memberA");

        //when
        //Long savedId = memberRepository.save(member);
        //Member findMember = memberRepository.find(savedId);

        //then
        /*Assertions.assertThat(findMember.getId()).isEqualTo(member.getId()); // assertj 의 Assertions 사용
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // jpa는 같은 객체를 가져온다.*/
    }

}