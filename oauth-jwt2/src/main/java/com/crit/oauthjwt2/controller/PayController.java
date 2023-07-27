package com.crit.oauthjwt2.controller;

import com.crit.oauthjwt2.common.security.SecurityUtil;
import com.crit.oauthjwt2.dto.pay.KakaoApproveResponse;
import com.crit.oauthjwt2.dto.pay.KakaoCancelResponse;
import com.crit.oauthjwt2.dto.pay.KakaoReadyResponse;
import com.crit.oauthjwt2.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PayController {
    private final KakaoService kakaoService;
    private final SecurityUtil securityUtil;

    /**
     * 결제 승인
     */
    @GetMapping("/pay")
    public ResponseEntity<KakaoReadyResponse> getPay(HttpServletRequest request, String amount) {

        String userId = getUserId(request);

        return ResponseEntity.ok(kakaoService.kakaoPayReady(userId, amount));
    }

    /**
     * 결제 성공
     */
    @GetMapping("/success")
    public ResponseEntity<KakaoApproveResponse> paySuccess(HttpServletRequest request, @RequestParam("pg_token") String pgToken) {

        String userId = getUserId(request);

        return ResponseEntity.ok(kakaoService.ApproveResponse(userId, pgToken));
    }

    /**
     * 결제 환불
     */
    @GetMapping("/refund")
    public ResponseEntity<KakaoCancelResponse> refund(HttpServletRequest request, @RequestParam("amount") String amount) {

        String userId = getUserId(request);

        return ResponseEntity.ok(kakaoService.kakaoCancel(userId, amount));
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public String payFail() {
        return "payment/fail";
    }

    /**
     * 결제 취소
     */
    @GetMapping("/cancel")
    public String payCancel() {
        return "payment/cancel";
    }

    private String getUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return (String) securityUtil.get(token).get("userId");
    }
}
