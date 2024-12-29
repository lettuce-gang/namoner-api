package com.toy.namoner.domain.user.model;


import com.toy.namoner.domain.user.model.enums.UserRole;
import com.toy.namoner.domain.user.model.enums.UserStatus;
import com.toy.namoner.domain.letter.model.Letter;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    public static User craeteNotRegisteredUser(String phoneNumber) {
        return User.builder()
                .phone(phoneNumber)
                .status(UserStatus.NOT_SIGNED)
                .postboxName(phoneNumber)
                .build();
    }


    public int getReceiveLettersCount() {
        return receiveLetters.size();
    }

    public boolean isFirstLoginUser() {
        return UserStatus.NOT_SIGNED == status;
    }


}
