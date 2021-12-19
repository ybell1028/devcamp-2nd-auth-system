package com.smilegate.auth.service;

import com.smilegate.auth.entity.Confirm;
import com.smilegate.auth.repository.ConfirmRepository;
import com.smilegate.auth.support.AuthError;
import com.smilegate.auth.support.AuthException;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmService {
    private final ConfirmRepository confirmRepository;
    private final JavaMailSender javaMailSender;

    public void sendMail(String receiver, String subject, String link) {
        MimeMessagePreparator messagePreparator =
                mimeMessage -> {
                    final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setFrom("noreply@noreply.com");
                    helper.setTo(receiver);
                    helper.setSubject(subject);
                    helper.setText(link, true);
                };
        javaMailSender.send(messagePreparator);
    }

    @Transactional
    @Async("mailExecutor") // 같이 쓰면 주의해야할 점에 대해 알아보자
    public void sendConfirm(UUID userUuid, String receiver){
        Confirm emailConfirm = Confirm.makeConfirm(userUuid);
        confirmRepository.save(emailConfirm);

        // 하드 코딩
        String subject = "회원가입 인증 메일";
        String link = String.format("<a href=\"http://localhost:8000/auth/confirm?sign=%s\">---> 링크를 눌러서 인증을 완료해주세요. <----</a>", emailConfirm.getId());
        this.sendMail(receiver, subject, link);
    }
    
    public Confirm findByIdAndExpirationDateAfterAndExpired(UUID confirmId){
        return confirmRepository.findByIdAndExpirationDateAfterAndExpired(confirmId, LocalDateTime.now(),false)
                .orElseThrow(()-> new AuthException(AuthError.BAD_REQUEST, "이메일 인증 토큰을 찾을 수 없습니다."));
    };
}
