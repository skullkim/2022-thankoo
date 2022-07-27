package com.woowacourse.thankoo.meeting.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MeetingQueryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
}
