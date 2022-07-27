package com.woowacourse.thankoo.acceptance.support.fixtures;

import static com.woowacourse.thankoo.acceptance.support.fixtures.RestAssuredRequestFixture.getWithToken;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MeetingRequestFixture {

    public static ExtractableResponse<Response> 쿠폰_만남_정보를_조회한다(final Long couponId, final String token) {
        return getWithToken("api/coupons/" + couponId + "/meetings", token);
    }
}
