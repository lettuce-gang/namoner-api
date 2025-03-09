package com.toy.namoner.domain.letter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toy.namoner.domain.letter.model.Letter;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LetterRepository extends JpaRepository<Letter, String> {

    Optional<Letter> findByReplyLetter_Id(String replyLetterId);

}
