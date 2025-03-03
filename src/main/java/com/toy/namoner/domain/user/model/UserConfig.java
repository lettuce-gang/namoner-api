package com.toy.namoner.domain.user.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserConfig {
    private Boolean showPostbox;

    private Boolean receiveLetter;

    private Boolean showLetterCount;
}
