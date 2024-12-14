package com.toy.namoneo.user.domain;

import com.toy.namoneo.letter.domain.Letter;
import com.toy.namoneo.user.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "nmn_user")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String phone;
    @OneToMany(mappedBy = "sendUser")
    private List<Letter> sendLetters = new ArrayList<>();

    @OneToMany(mappedBy = "receiveUser")
    private List<Letter> receiveLetters = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public static User craeteNotRegisteredUser() {
        return User.builder()
                .status(UserStatus.NOT_SIGNED)
                .build();
    }


    public int getReceiveLettersCount() {
        return receiveLetters.size();
    }


}
