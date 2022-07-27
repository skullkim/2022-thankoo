package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.로그인_한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.AuthenticationRequestFixture.토큰을_반환한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.createCouponRequest;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.받은_쿠폰을_조회한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.CouponRequestFixture.쿠폰을_전송한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.MeetingRequestFixture.*;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.에약을_요청한다;
import static com.woowacourse.thankoo.acceptance.support.fixtures.ReservationRequestFixture.예약을_승인한다;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HOHO;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.acceptance.support.fixtures.MeetingRequestFixture;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.meeting.presentation.dto.MeetingResponse;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@DisplayName("MeetingAcceptanceTest 는 ")
public class MeetingAcceptanceTest extends AcceptanceTest {

    @DisplayName("쿠폰을 통해 만남을 조회한다.")
    @Test
    void getMeetingByCoupon() {
        TokenResponse senderToken = 토큰을_반환한다(로그인_한다(CODE_SKRR));
        TokenResponse receiverToken = 토큰을_반환한다(로그인_한다(CODE_HOHO));

        CouponRequest couponRequest = createCouponRequest(List.of(receiverToken.getMemberId()), TYPE, TITLE, MESSAGE);
        쿠폰을_전송한다(senderToken.getAccessToken(), couponRequest);

        CouponResponse couponResponse = 받은_쿠폰을_조회한다(receiverToken.getAccessToken(), NOT_USED).jsonPath()
                .getList(".", CouponResponse.class).get(0);

        String reservationId = 에약을_요청한다(receiverToken.getAccessToken(),
                new ReservationRequest(couponResponse.getCouponId(), LocalDateTime.now().plusDays(1L)))
                .header(HttpHeaders.LOCATION).split("reservations/")[1];

        예약을_승인한다(reservationId, senderToken.getAccessToken());

        ExtractableResponse<Response> response = 쿠폰_만남_정보를_조회한다(couponResponse.getCouponId(),
                senderToken.getAccessToken());
        단건_만남이_조회됨(response);
    }

    private void 단건_만남이_조회됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.as(MeetingResponse.class)).isNotNull();
    }
}
