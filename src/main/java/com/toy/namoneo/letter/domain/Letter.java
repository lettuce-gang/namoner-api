package com.toy.namoneo.letter.domain;

import com.toy.namoneo.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoneo.letter.domain.enums.FontType;
import com.toy.namoneo.letter.domain.enums.LetterPaperType;
import com.toy.namoneo.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
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

    private String message;

    @Enumerated(EnumType.STRING)
    private FontType fontType;

    @Enumerated(EnumType.STRING)
    private LetterPaperType letterPaperType;

    private String imageUrl;

    public static Letter from(LetterSendRequest request, User userReceiver, String imagerUrl) {
        return Letter.builder()
                .userReceiver(userReceiver)
                .letterReceiver(request.getLetterReceiver())
                .letterSender(request.getLetterSender())
                .imageUrl(imagerUrl)
                .message(request.getMessage())
                .fontType(request.getFontType())
                .letterPaperType(request.getLetterPaperType())
                .build();
    }

}
