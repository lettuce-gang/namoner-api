package com.toy.namoner.domain.stat.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.toy.namoner.domain.letter.model.Letter;
import com.toy.namoner.domain.letter.model.enums.FontType;
import com.toy.namoner.domain.letter.model.enums.LetterPaperType;
import com.toy.namoner.domain.letter.model.enums.LetterType;
import com.toy.namoner.domain.stat.model.enums.LetterActionType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Document(indexName = "stat-letter")
public class LetterStat {
	@Id
	private String id;

	@CreatedDate
	@Field(name = "@timestamp", type = FieldType.Date)
	private Date timestamp;

	private LetterActionType actionType;
	private String letterId;
	private String letterSender;
	private String letterReceiver;
	private String message;
	private LetterType letterType;
	private FontType fontType;
	private LetterPaperType letterPaperType;

	public static LetterStat.LetterStatBuilder builderFrom(Letter letter) {
		return LetterStat.builder()
				.letterId(letter.getId())
				.letterSender(letter.getLetterSender())
				.letterReceiver(letter.getLetterReceiver())
				.message(letter.getMessage())
				.letterType(letter.getLetterType())
				.fontType(letter.getFontType())
				.letterPaperType(letter.getLetterPaperType());
	}
}
