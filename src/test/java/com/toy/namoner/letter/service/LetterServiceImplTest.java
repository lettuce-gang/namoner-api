package com.toy.namoner.letter.service;

import com.toy.namoner.letter.controller.dto.response.LetterListResponse;
import com.toy.namoner.letter.domain.Letter;
import com.toy.namoner.letter.domain.enums.LetterType;
import com.toy.namoner.letter.repository.LetterRepository;
import com.toy.namoner.user.domain.User;
import com.toy.namoner.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class LetterServiceImplTest {
    @Mock
    private UserService userService;

    @Mock
    private LetterRepository letterRepository;

    @InjectMocks
    private LetterServiceImpl letterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 편지지들은 다음 순서롤 정렬되어야 한다.
     * 1. 예약 X 읽음 X 최신 도착 순 (receivedDate desc)
     * 2. 예약 O 읽음 X 예약이 가장 조금 남은 순 (receivedDate asc)
     * 3. 읽음 O 최신 도착 순 (receivedDate desc)
     */
    @Test
    void findLettersByUserId_shouldReturnSortedLetters() {
        // Given
        String userId = "user1";

        Letter reservedLetter01 = createLetter("reservedLetter01",false, LetterType.RESERVED, LocalDateTime.of(2024, 12, 25, 10, 0));
        Letter reservedLetter02 = createLetter("reservedLetter02", false, LetterType.RESERVED, LocalDateTime.of(2024, 12, 28, 8, 0));

        Letter notReadLetter01 = createLetter("normalNotReadLetter01", false, LetterType.NORMAL, LocalDateTime.of(2024, 12, 20, 9, 0));
        Letter notReadLetter02 = createLetter("normalNotReadLetter02", false, LetterType.NORMAL, LocalDateTime.of(2024, 12, 21, 11, 0));

        Letter readLetter01 = createLetter("normalReadLetter01", true, LetterType.NORMAL, LocalDateTime.of(2024, 12, 18, 9, 0));
        Letter readLetter02 = createLetter("normalReadLetter02", true, LetterType.NORMAL, LocalDateTime.of(2024, 12, 20, 5, 0));

        List<Letter> userLetters = Arrays.asList(reservedLetter01, reservedLetter02, notReadLetter01, notReadLetter02,  readLetter01, readLetter02);

        User mockUser = User.builder()
                .id(userId)
                .receiveLetters(userLetters)
                .build();

        when(userService.findByUserId(userId)).thenReturn(mockUser);

        // When
        List<LetterListResponse> result = letterService.findLettersByUserId(userId);

        // Then
        assertThat(result).hasSize(6);


        assertThat(result.get(0).getId()).isEqualTo("normalNotReadLetter02");
        assertThat(result.get(0).getReceiveDate()).isEqualTo(LocalDateTime.of(2024, 12, 21, 11, 0)); // Not Read Letter
        assertThat(result.get(1).getId()).isEqualTo("normalNotReadLetter01");
        assertThat(result.get(1).getReceiveDate()).isEqualTo(LocalDateTime.of(2024, 12, 20, 9, 0)); // Not Read Letter

        assertThat(result.get(2).getId()).isEqualTo("reservedLetter01");
        assertThat(result.get(2).getReceiveDate()).isEqualTo(LocalDateTime.of(2024, 12, 25, 10, 0)); // reserved Letter
        assertThat(result.get(3).getId()).isEqualTo("reservedLetter02");
        assertThat(result.get(3).getReceiveDate()).isEqualTo(LocalDateTime.of(2024, 12, 28, 8, 0)); // reserved Letter


        assertThat(result.get(4).getId()).isEqualTo("normalReadLetter02");
        assertThat(result.get(4).getReceiveDate()).isEqualTo(LocalDateTime.of(2024, 12, 20, 5, 0)); // Read Letter
        assertThat(result.get(5).getId()).isEqualTo("normalReadLetter01");
        assertThat(result.get(5).getReceiveDate()).isEqualTo(LocalDateTime.of(2024, 12, 18, 9, 0)); // Read Letter
    }

    private Letter createLetter(String id, boolean isRead, LetterType letterType, LocalDateTime receiveDate) {

        return Letter.builder()
                .id(id)
                .isRead(isRead)
                .letterType(letterType)
                .receiveDate(receiveDate)
                .build();
    }

}