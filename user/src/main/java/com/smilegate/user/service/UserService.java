package com.smilegate.user.service;

import com.fasterxml.uuid.Generators;
import com.smilegate.user.dto.RegisterRequest;
import com.smilegate.user.entity.Confirmation;
import com.smilegate.user.entity.Role;
import com.smilegate.user.entity.User;
import com.smilegate.user.entity.vo.UserVo;
import com.smilegate.user.repository.UserRepository;
import com.smilegate.user.support.UserError;
import com.smilegate.user.support.UserException;
import com.smilegate.user.utils.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CryptoUtils cryptoUtils;
    private final ConfirmationService confirmationService;

    @Transactional
    public User register(RegisterRequest registerRequest) {
        UserServiceUtils.validateNotExistsUserEmail(userRepository, registerRequest.getEmail());

        String encryted = "";
        try {
            encryted = cryptoUtils.encrypt(registerRequest.getPassword());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UserException(UserError.PASSWORD_CONVERT_ERROR, ex.getCause());
        }

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .uuid(Generators.timeBasedGenerator().generate())
                .password(encryted)
                .role(Role.ASSOCIATE)
                .build();
        userRepository.save(user);
        confirmationService.sendConfirmation(user.getUuid(), user.getEmail());
        return user;
    }

    @Transactional(readOnly = true)
    public User findByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserException(UserError.BAD_REQUEST, "???????????? ?????? ???????????????."));
    }

    @Transactional(readOnly = true)
    public List<UserVo> findUserAll() { // ??????????
        return userRepository.findAll().stream()
                .map(user -> UserVo.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole().getTitle())
                        .uuid(user.getUuid())
                        .createdAt(user.getCreatedAt())
                        .modifiedAt(user.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public String findPassword(String encrypted) throws Exception {
        return cryptoUtils.decrypt(encrypted);
    }

    @Transactional
    public void deleteByUuid(UUID uuid) {
        userRepository.deleteByUuid(uuid);
    }

    @Transactional
    public void confirmEmail(UUID confirmationId) {
        Confirmation confirmation = confirmationService.findByIdAndExpirationDateAfterAndExpired(confirmationId);
        User user = findByUuid(confirmation.getUserUuid());
        confirmation.useToken();
        user.modifyRole(Role.ADMIN);
    }
}
