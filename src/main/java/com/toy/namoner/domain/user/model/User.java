package com.toy.namoner.domain.user.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.toy.namoner.common.model.BaseEntity;
import com.toy.namoner.domain.auth.role.UserRole;
import com.toy.namoner.domain.letter.model.Letter;
import com.toy.namoner.domain.user.controller.dto.request.UserJoinRequest;
import com.toy.namoner.domain.user.model.enums.Gender;
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
@SQLRestriction(value = "status <> 'DISABLED'")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String phone;

    private String age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "userSender")
    private List<Letter> sendLetters = new ArrayList<>();

    @OneToMany(mappedBy = "userReceiver")
    private List<Letter> receiveLetters = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String postboxName;

    private Boolean isPhoneConnected;

    public static User from(UserJoin userJoin) {
        return User.builder()
            .phone(userJoin.getPhoneNum())
            .status(UserStatus.NOT_SIGNED)
            .postboxName(userJoin.getPhoneNum())
            .role(UserRole.USER)
            .isPhoneConnected(true)
			.gender(userJoin.getGender())
			.age(userJoin.getAge())
			.build();
    }


    public int getReceiveLettersCount() {
        return receiveLetters.size();
    }

    public int getUnreadLetterCount() {
        return (int) receiveLetters.stream()
                .filter(letter -> !letter.getIsRead())
                .count();
    }

    public boolean isSignedUser() {
        return UserStatus.SIGNED == status;
    }

    public void firstUpdateUserInfo(UserJoinRequest updateInfo) {
        postboxName = updateInfo.getPostBoxName();
        isPhoneConnected = updateInfo.getIsPhoneConnected();
        status = UserStatus.SIGNED;
    }

    public void setToDisable() {
        this.status = UserStatus.DISABLED;
    }
}
