package com.woowacourse.thankoo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("MemberRepository 는")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버를 저장한다.")
    @Test
    void save() {
        Member member = new Member("lala");

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isEqualTo(member);
    }

    @DisplayName("이름으로 멤버를 조회한다.")
    @Test
    void findByName() {
        Member member = new Member("lala");
        memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findByName("lala");

        assertAll(
                () -> assertThat(foundMember).isNotEmpty(),
                () -> assertThat(foundMember.orElseThrow()).isEqualTo(member)
        );
    }
}
