package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.member.domain.Member;
import lombok.Getter;

@Getter
public class MeetingCouponMember {

    private Long meetingId;
    private Member member;
}
