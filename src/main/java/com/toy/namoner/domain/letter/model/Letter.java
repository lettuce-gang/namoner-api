package com.toy.namoner.domain.letter.model;

import java.time.LocalDateTime;

import com.toy.namoner.domain.letter.controller.dto.request.LetterReplyRequest;
import com.toy.namoner.common.model.BaseEntity;
import com.toy.namoner.domain.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoner.domain.letter.model.enums.FontType;
import com.toy.namoner.domain.letter.model.enums.LetterPaperType;
import com.toy.namoner.domain.letter.model.enums.LetterType;
import com.toy.namoner.domain.user.model.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Letter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userSender;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userReceiver;

    private String letterSender;
    private String letterReceiver;

    private String message;
    private LocalDateTime receiveDate;
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    private LetterType letterType;
    @Enumerated(EnumType.STRING)
    private FontType fontType;

    @Enumerated(EnumType.STRING)
    private LetterPaperType letterPaperType;

    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    private Letter replyLetter;

    public static Letter createNormalLetterType(LetterSendRequest request, User userReceiver, User userSender, String imagerUrl) {
        LocalDateTime now = LocalDateTime.now();

        return Letter.builder()
                .userReceiver(userReceiver)
                .userSender(userSender)
                .letterSender(request.getLetterSender())
                .letterReceiver(request.getLetterReceiver())
                .message(request.getMessage())
                .letterPaperType(request.getLetterPaperType())
                .fontType(request.getFontType())
                .letterType(LetterType.NORMAL)
                .receiveDate(now)
                .imageUrl(imagerUrl)
                .isRead(false)
                .build();
    }

    public static Letter createReservedLetterType(LetterSendRequest request, User userReceiver, User userSender, String imagerUrl) {
        return Letter.builder()
                .userReceiver(userReceiver)
                .userSender(userSender)
                .letterSender(request.getLetterSender())
                .letterReceiver(request.getLetterReceiver())
                .message(request.getMessage())
                .letterPaperType(request.getLetterPaperType())
                .fontType(request.getFontType())
                .letterType(LetterType.RESERVED)
                .receiveDate(request.getReceiveDate())
                .imageUrl(imagerUrl)
                .isRead(false)
                .build();
    }

    public static Letter createReplyLetterType(LetterReplyRequest request, User userReceiver, User userSender, String imagerUrl) {
        return Letter.builder()
                .userReceiver(userReceiver)
                .userSender(userSender)
                .letterSender(request.getLetterSender())
                .letterReceiver(request.getLetterReceiver())
                .message(request.getMessage())
                .letterPaperType(request.getLetterPaperType())
                .fontType(request.getFontType())
                .letterType(LetterType.REPLY)
                .receiveDate(request.getReceiveDate())
                .imageUrl(imagerUrl)
                .isRead(false)
                .build();
    }
    public static Letter createBySend(LetterSendRequest request, User userReceiver, String imagerUrl) {
        return Letter.builder()
                .userReceiver(userReceiver)
                .letterSender(request.getLetterSender())
                .letterReceiver(request.getLetterReceiver())
                .message(request.getMessage())
                .letterPaperType(request.getLetterPaperType())
                .fontType(request.getFontType())
                .letterType(LetterType.RESERVED)
                .receiveDate(request.getReceiveDate())
                .imageUrl(imagerUrl)
                .isRead(false)
                .build();
    }

    public void readLetter() {
        this.isRead = true;
    }

    public boolean checkUserReceiver(User user) {
        return this.userReceiver.equals(user);
    }
    public boolean isCanReply() {
        if (this.letterType == LetterType.REPLY) {
            return false;
        }
        return this.userSender != null;
    }

    public void replyLetter(Letter replyLetter) {
        this.replyLetter = replyLetter;
    }

}
