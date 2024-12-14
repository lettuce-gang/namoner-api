package com.toy.namoneo.letter.repository;

import com.toy.namoneo.letter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, String> {

}
