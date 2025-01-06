package com.toy.namoner.domain.user.model;

import java.util.ArrayList;
import java.util.List;

import com.toy.namoner.domain.letter.model.Letter;
import com.toy.namoner.domain.user.controller.dto.request.UserInfoUpdateRequest;
import com.toy.namoner.domain.user.model.enums.UserRole;
import com.toy.namoner.domain.user.model.enums.UserStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "nmn_user")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String phone;
    @OneToMany(mappedBy = "userSender")
    private List<Letter> sendLetters = new ArrayList<>();

    @OneToMany(mappedBy = "userReceiver")
    private List<Letter> receiveLetters = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String postboxName;

    private Boolean isPhoneConnected;

    public static User craeteNotRegisteredUser(String phoneNumber) {
        return User.builder()
                .phone(phoneNumber)
                .status(UserStatus.NOT_SIGNED)
                .postboxName(phoneNumber)
                .role(UserRole.USER)
                .build();
    }


    public int getReceiveLettersCount() {
        return receiveLetters.size();
    }

    public boolean isFirstLoginUser() {
        return UserStatus.NOT_SIGNED == status;
    }


    public void firstUpdateUserInfo(UserInfoUpdateRequest updateInfo) {
        postboxName = updateInfo.getPostBoxName();
        isPhoneConnected = updateInfo.getIsPhoneConnected();
        status = UserStatus.SIGNED;
    }
}
