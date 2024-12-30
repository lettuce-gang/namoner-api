package com.toy.namoner.domain.letter.repository;

import com.toy.namoner.domain.letter.model.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, String> {

}
