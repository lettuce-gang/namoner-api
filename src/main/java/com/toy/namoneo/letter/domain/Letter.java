package com.toy.namoneo.letter.domain;

import com.toy.namoneo.letter.controller.dto.LetterSendRequest;
import com.toy.namoneo.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userSender;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userReceiver;

    private String letterSender;
    private String letterReceiver;

    private String text;
    private String imageUrl;

    public static Letter from(LetterSendRequest request, User userReceiver, String imagerUrl) {
        return Letter.builder()
                .userReceiver(userReceiver)
                .letterReceiver(request.getLetterReceiver())
                .letterSender(request.getLetterSender())
                .imageUrl(imagerUrl)
                .text(request.getText())
                .build();
    }

}
