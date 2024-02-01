package com.vvh.coresv.service;

import com.vvh.coresv.repository.MeetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MeetingServiceImplTest.class)
class MeetingServiceImplTest {

    @MockBean
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingService meetingService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllBuySubjectId() {

    }
}