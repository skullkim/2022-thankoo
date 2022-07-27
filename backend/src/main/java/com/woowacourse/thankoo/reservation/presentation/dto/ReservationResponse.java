package com.woowacourse.thankoo.reservation.presentation.dto;

import com.woowacourse.thankoo.reservation.domain.ReservationCoupon;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationResponse {

    private ReservationInformationResponse reservation;
    private String memberName;
    private String couponType;

    private ReservationResponse(final ReservationInformationResponse reservation,
                                final String memberName,
                                final String couponType) {
        this.reservation = reservation;
        this.memberName = memberName;
        this.couponType = couponType.toLowerCase(Locale.ROOT);
    }

    public static ReservationResponse from(final ReservationCoupon reservationCoupon) {
        return new ReservationResponse(
                ReservationInformationResponse.of(reservationCoupon.getReservationId(),
                        reservationCoupon.getMeetingTime()),
                reservationCoupon.getMemberName(),
                reservationCoupon.getCouponType());
    }
}
