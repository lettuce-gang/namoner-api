package com.toy.namoner.domain.user.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserConfig {
    private Boolean showPostbox;

    private Boolean receiveLetter;

    private Boolean showLetterCount;

    public static UserConfig createDefault() {
        return new UserConfig(true, true, true);
    }
}
