package com.toy.namoner.letter.repository;

import com.toy.namoner.letter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, String> {

}
