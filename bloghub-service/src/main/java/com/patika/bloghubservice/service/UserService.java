package com.patika.bloghubservice.service;


import com.patika.bloghubservice.converter.UserConverter;
import com.patika.bloghubservice.dto.request.ChangePasswordRequest;
import com.patika.bloghubservice.dto.request.ChangeStatusBulkRequest;
import com.patika.bloghubservice.dto.request.UserSaveRequest;
import com.patika.bloghubservice.dto.response.UserResponse;
import com.patika.bloghubservice.exception.BlogHubException;
import com.patika.bloghubservice.exception.ExceptionMessages;
import com.patika.bloghubservice.model.User;
import com.patika.bloghubservice.model.enums.StatusType;
import com.patika.bloghubservice.model.enums.UserType;
import com.patika.bloghubservice.repository.UserRepository;
import com.patika.bloghubservice.util.HashPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserResponse saveUser(UserSaveRequest request) {

        if (request.getEmail() == null) {
            log.error("request: {},", request + "\n" + ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
            throw new BlogHubException(ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
        }

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            throw new BlogHubException(ExceptionMessages.USER_ALREADY_DEFINED);
        } else {
            User savedUser = new User(request.getEmail(), HashPassword.hash(request.getPassword())); // ödev password' hash'le

            userRepository.save(savedUser);

            return UserConverter.toResponse(savedUser);
        }
    }
    protected User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BlogHubException(ExceptionMessages.USER_NOT_FOUND));
    }

    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user bulunamadı"));

        return UserConverter.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserConverter.toResponse(users);
    }

    public void changeStatus(String email, StatusType statusType) {
        User foundUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new BlogHubException(ExceptionMessages.USER_NOT_FOUND));

        foundUser.setStatusType(statusType);

        userRepository.updateUser(email, foundUser);
    }

    public List<String> changeStatusBulk(ChangeStatusBulkRequest changeStatusBulkRequest) {
        changeStatusBulkRequest.emailList().forEach(email -> changeStatus(email, changeStatusBulkRequest.userType()));
        return changeStatusBulkRequest.emailList();
    }



    // 8. soru a şıkkı : Kullanıcı şifresini değiştiren endpoint
    public void changeUserPassword(ChangePasswordRequest changePasswordRequest) {
        User user = getByEmail(changePasswordRequest.email());
        String hashedPassword = HashPassword.hash(changePasswordRequest.oldPassword());

        if(!hashedPassword.equals(user.getPassword()))
            throw new BlogHubException(ExceptionMessages.PASSWORD_INCORRECT);

        user.setPassword(HashPassword.hash(changePasswordRequest.newPassword()));

        userRepository.updateUser(changePasswordRequest.email(), user);
    }

    public void changeUserType(String email, UserType userType) {
        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new BlogHubException(ExceptionMessages.USER_NOT_FOUND));

        if(!foundUser.getStatusType().equals(StatusType.APPROVED)){
            throw new BlogHubException(ExceptionMessages.USER_STATUS_NOT_APPROVED);
        }

        foundUser.setUserType(userType);

        userRepository.updateUser(email, foundUser);
    }
}
