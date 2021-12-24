package com.smilegate.user.service;

import com.smilegate.user.entity.Confirmation;
import com.smilegate.user.repository.ConfirmationRepository;
import com.smilegate.user.support.UserError;
import com.smilegate.user.support.UserException;
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
public class ConfirmationService {
    private final ConfirmationRepository confirmationRepository;
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
    public void sendConfirmation(UUID userUuid, String receiver){
        Confirmation emailConfirmation = Confirmation.makeConfirm(userUuid);
        confirmationRepository.save(emailConfirmation);

        // 하드 코딩
        String subject = "회원가입 인증 메일";
        String link = String.format("<a href=\"http://localhost:8090/confirmation/?sign=%s\">----> 링크를 눌러서 인증을 완료해주세요. <----</a>", emailConfirmation.getId());
        this.sendMail(receiver, subject, link);
    }

    @Transactional(readOnly = true)
    public Confirmation findByIdAndExpirationDateAfterAndExpired(UUID confirmationId){
        return confirmationRepository.findByIdAndExpirationDateAfterAndExpired(confirmationId, LocalDateTime.now(),false)
                .orElseThrow(()-> new UserException(UserError.BAD_REQUEST, "이메일 인증 토큰을 찾을 수 없습니다."));
    };
}
