package com.jszarski.bookapi.service;

import com.jszarski.bookapi.client.BookServiceClient;
import com.jszarski.common.model.dto.SubscriptionDTO;
import com.jszarski.common.model.dto.SubscriptionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    private static final String EMAIL = "email";
    private static final String AUTHOR = "author";
    private static final String GENRE = "fantasy";
    private static final Double RATING_AVG = 6.5;
    private static final SubscriptionType TYPE = SubscriptionType.TOP_RATED;

    @Mock
    private BookServiceClient client;

    @InjectMocks
    private SubscriptionService service;

    @Test
    public void subscribe() {
        //given
        SubscriptionDTO dto = SubscriptionDTO.builder()
                .email(EMAIL)
                .author(AUTHOR)
                .genre(GENRE)
                .ratingAvg(RATING_AVG)
                .type(TYPE)
                .build();

        //when
        service.subscribe(dto);

        //then
        verify(client, times(1)).subscribe(dto);
    }

    @Test
    public void getSubscriptions() {
        //given
        when(client.getSubscriptions(EMAIL)).thenReturn(List.of(new SubscriptionDTO(), new SubscriptionDTO()));

        //when
        var result = service.getSubscriptions(EMAIL);

        //then
        verify(client, times(1)).getSubscriptions(EMAIL);
        assertEquals(2, result.size());
    }

}