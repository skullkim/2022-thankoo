package com.woowacourse.thankoo.member.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
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

    @DisplayName("회원를 저장한다.")
    @Test
    void save() {
        Member member = new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL);

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isEqualTo(member);
    }

    @DisplayName("이름으로 회원을 조회한다.")
    @Test
    void findByName() {
        Member member = new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL);
        memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findByName(HOHO_NAME);

        assertAll(
                () -> assertThat(foundMember).isNotEmpty(),
                () -> assertThat(foundMember.orElseThrow()).isEqualTo(member)
        );
    }

    @DisplayName("이름 순서대로 회원을 조회한다.")
    @Test
    void findAllByOrderByNameAsc() {
        Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));

        List<Member> members = memberRepository.findAllByIdNotOrderByNameAsc(member.getId());

        assertThat(members).extracting("name").containsExactly(HOHO_NAME, HUNI_NAME);
    }

    @DisplayName("소셜 아이디로 회원을 조회한다.")
    @Test
    void findBySocialId() {
        Member member = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));

        Optional<Member> foundMember = memberRepository.findBySocialId(HOHO_SOCIAL_ID);

        assertAll(
                () -> assertThat(foundMember).isNotEmpty(),
                () -> assertThat(foundMember.orElseThrow()).isEqualTo(member)
        );
    }
}
