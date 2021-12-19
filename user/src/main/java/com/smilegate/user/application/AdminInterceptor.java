package com.smilegate.user.application;

import com.smilegate.user.entity.Role;
import com.smilegate.user.entity.User;
import com.smilegate.user.repository.UserRepository;
import com.smilegate.user.support.UserError;
import com.smilegate.user.support.UserException;
import com.smilegate.user.utils.CertificateAuthority;
import com.smilegate.user.utils.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;
    private final CertificateAuthority certificateAuthority;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // String requestHttpMethod = request.getMethod();
        // Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        TokenPayload payload = certificateAuthority.decrypt(request.getHeader("x-access-token"));

        User user = userRepository.findByUuid(payload.getUuid())
                .orElseThrow(() -> new UserException(UserError.BAD_REQUEST, "존재하지 않는 계정입니다."));

        if(user.getRole().getRank() < Role.ADMIN.getRank()) {
            throw new UserException(UserError.NOT_PERMITTED);
        }

        if(user.getUuid().equals(payload.getUuid())) {
            request.setAttribute("user", user);
            return true;
        }
        else {
            throw new UserException(UserError.NOT_LOGIN, "토큰과 로그인 정보가 일치하지 않습니다.");
        }
    }
}
