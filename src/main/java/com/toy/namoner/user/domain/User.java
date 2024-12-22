package com.toy.namoner.user.domain;

import com.toy.namoner.letter.domain.Letter;
import com.toy.namoner.user.domain.enums.UserStatus;
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


}
