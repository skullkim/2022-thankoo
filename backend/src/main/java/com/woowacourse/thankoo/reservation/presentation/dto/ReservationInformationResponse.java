package com.woowacourse.thankoo.reservation.presentation.dto;

import com.woowacourse.thankoo.reservation.domain.Reservation;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationInformationResponse {

    private Long id;
    private LocalDateTime meetingTime;

    private ReservationInformationResponse(final Long id, final LocalDateTime meetingTime) {
        this.id = id;
        this.meetingTime = meetingTime;
    }

    public static ReservationInformationResponse of(final Long reservationId, final LocalDateTime meetingTime) {
        return new ReservationInformationResponse(reservationId, meetingTime);
    }

    public static ReservationInformationResponse from(final Reservation reservation) {
        return new ReservationInformationResponse(reservation.getId(), reservation.getMeetingTime().getTime());
    }
}
