package com.toy.namoner.domain.stat.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.toy.namoner.domain.stat.model.enums.UserActionType;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.model.enums.Gender;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Document(indexName = "stat-user")
public class UserStat {
	@Id
	private Long id;

	@CreatedDate
	@Field(name = "@timestamp", type = FieldType.Date)
	private Date timestamp;

	private UserActionType actionType;
	private String userId;
	private Gender gender;
	private String age;
	private String referrer;
	private String reason;

	public static UserStat.UserStatBuilder builderFrom(User user) {
		return UserStat.builder()
			.userId(user.getId());
	}
}
