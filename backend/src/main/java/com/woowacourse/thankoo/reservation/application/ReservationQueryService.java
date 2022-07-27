package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.common.exception.ForbiddenException;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationCoupon;
import com.woowacourse.thankoo.reservation.domain.ReservationQueryRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationInformationResponse;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationQueryRepository reservationQueryRepository;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    public List<ReservationResponse> getReceivedReservations(final Long memberId) {
        Member member = getMemberById(memberId);
        List<ReservationCoupon> receivedReservations = reservationQueryRepository.findReceivedReservations(
                member.getId(), LocalDateTime.now());
        return toReservationResponses(receivedReservations);
    }

    public List<ReservationResponse> getSentReservations(final Long memberId) {
        Member member = getMemberById(memberId);
        List<ReservationCoupon> sentReservations = reservationQueryRepository.findSentReservations(member.getId(),
                LocalDateTime.now());
        return toReservationResponses(sentReservations);
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private List<ReservationResponse> toReservationResponses(final List<ReservationCoupon> receivedReservations) {
        return receivedReservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    public ReservationInformationResponse getByCouponId(final Long memberId, final Long couponId) {
        Reservation reservation = reservationRepository.findTopByCoupon_IdAndReservationStatus(
                        couponId, ReservationStatus.WAITING)
                .orElseThrow(() -> new InvalidReservationException(ErrorType.NOT_FOUND_RESERVATION));
        Coupon coupon = reservation.getCoupon();
        Member member = getMemberById(memberId);
        validateMemberConfirmed(coupon, member);
        return ReservationInformationResponse.from(reservation);
    }

    private void validateMemberConfirmed(final Coupon coupon, final Member member) {
        if (!member.isOwner(coupon.getReceiverId(), coupon.getSenderId())) {
            throw new ForbiddenException(ErrorType.FORBIDDEN);
        }
    }
}
