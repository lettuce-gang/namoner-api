package com.toy.namoner.domain.letter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toy.namoner.domain.letter.model.Letter;

public interface LetterRepository extends JpaRepository<Letter, String> {

}
