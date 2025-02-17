package com.toy.namoner.domain.stat.repository;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Repository;

import com.toy.namoner.common.utils.ElasticIndexUtils;
import com.toy.namoner.domain.stat.model.LetterStat;
import com.toy.namoner.domain.stat.model.UserStat;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StatRepository {
	private final ElasticsearchOperations esOps;

	public UserStat logUser(UserStat userStat) {
		return esOps.save(userStat, IndexCoordinates.of(ElasticIndexUtils.getIndex(UserStat.class)));
	}

	public LetterStat logLetter(LetterStat letterStat) {
		return esOps.save(letterStat, IndexCoordinates.of(ElasticIndexUtils.getIndex(LetterStat.class)));
	}
}
