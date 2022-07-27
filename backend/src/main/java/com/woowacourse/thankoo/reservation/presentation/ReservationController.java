package com.woowacourse.thankoo.reservation.presentation;

import com.woowacourse.thankoo.authentication.presentation.AuthenticationPrincipal;
import com.woowacourse.thankoo.reservation.application.ReservationQueryService;
import com.woowacourse.thankoo.reservation.application.ReservationService;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationInformationResponse;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationQueryService reservationQueryService;

    @PostMapping("/reservations")
    public ResponseEntity<Void> reserve(@AuthenticationPrincipal final Long memberId,
                                        @RequestBody final ReservationRequest reservationRequest) {
        Long reserveId = reservationService.save(memberId, reservationRequest);
        return ResponseEntity.created(URI.create("/api/reservations/" + reserveId)).build();
    }

    @GetMapping("/reservations/received")
    public ResponseEntity<List<ReservationResponse>> getReceivedReservations(
            @AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(reservationQueryService.getReceivedReservations(memberId));
    }

    @GetMapping("/reservations/sent")
    public ResponseEntity<List<ReservationResponse>> getSentReservations(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(reservationQueryService.getSentReservations(memberId));
    }

    @PutMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> updateStatus(@AuthenticationPrincipal final Long memberId,
                                             @PathVariable final Long reservationId,
                                             @RequestBody final ReservationStatusRequest reservationStatusRequest) {
        reservationService.updateStatus(memberId, reservationId, reservationStatusRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/coupons/{couponId}/reservations")
    public ResponseEntity<ReservationInformationResponse> getByCouponId(@AuthenticationPrincipal final Long memberId,
                                                                        @PathVariable final Long couponId) {
        return ResponseEntity.ok(reservationQueryService.getByCouponId(memberId, couponId));
    }
}
