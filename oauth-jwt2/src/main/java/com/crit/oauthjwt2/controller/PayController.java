package com.crit.oauthjwt2.controller;

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
    private KakaoService kakaoService;

    @PostMapping("/pay")
    public ResponseEntity<KakaoReadyResponse> getPay(HttpServletRequest request, String amount) {
        // 추후 챌린지 정보에서 금액을 받아와서 로직 작성
        String token = request.getHeader("Authorization").substring(7);
        System.out.println("==========pay===== " + amount);
        return ResponseEntity.ok(kakaoService.kakaoPayReady());
    }

    @GetMapping("/success")
    public ResponseEntity<KakaoApproveResponse> paySuccess(@RequestParam("pg_token") String pgToken) {
        return ResponseEntity.ok(kakaoService.ApproveResponse(pgToken));
    }

    @GetMapping("/fail")
    public String payFail() {
        return "payment/fail";
    }

    @GetMapping("/cancel")
    public String payCancel() {
        return "payment/cancel";
    }

    @PostMapping("/refund")
    public ResponseEntity<KakaoCancelResponse> refund() {
        return ResponseEntity.ok(kakaoService.kakaoCancel());
    }
}
