package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입")
    void MemberJoinTest() {
        //given
        Member member = new Member();
        member.setName("choi");

        //when
        Long saveMember = memberService.join(member);

        //then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveMember));
    }

    @Test
    @DisplayName("중복 회원 예외")
    void DuplicateMemberTest() {
        //given
        Member member1 = new Member();
        member1.setName("choi");

        Member member2 = new Member();
        member2.setName("choi");

        //when
        memberService.join(member1);
        //then
        Assertions.assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalStateException.class); // 예외 발생


    }

}