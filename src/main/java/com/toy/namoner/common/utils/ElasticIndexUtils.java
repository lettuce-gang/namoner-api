package com.toy.namoner.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Component;

@Component
public class ElasticIndexUtils {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

	public static String getIndex(Class<?> clazz) {
		return "%s-%s".formatted(getIndexPrefix(clazz), DATE_TIME_FORMATTER.format(LocalDate.now()));
	}

	private static String getIndexPrefix(Class<?> clazz) {
		Document docAnnotation = AnnotationUtils.findAnnotation(clazz, Document.class);
		if (docAnnotation == null) {
			throw new IllegalArgumentException("Document annotation is not found in %s".formatted(clazz.getName()));
		}

		return docAnnotation.indexName();
	}
}
