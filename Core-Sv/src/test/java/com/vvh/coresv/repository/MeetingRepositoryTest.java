package com.vvh.coresv.repository;

import com.vvh.coresv.dto.MeetingDTO;
import com.vvh.coresv.entity.Meeting;
import com.vvh.coresv.entity.Subject;
import com.vvh.coresv.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MeetingRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MeetingRepository meetingRepository;

    @BeforeEach
    void init(){
        User user = User.builder()
                .id("6656485")
                .name("VuVanHoang")
                .build();
        Subject subject = Subject.builder()
                .subjectName("testSubject")
                .user(user)
                .build();
        Set<Subject> subjectSet = new HashSet<>();
        subjectSet.add(subject);
        user.setSubjectSet(subjectSet);
        entityManager.persist(user);

        Set<Meeting> meetingSet = new HashSet<>();
        for(int i = 0; i<10; i++){
            Meeting meeting = Meeting.builder()
                    .subject(subject)
                    .build();
            entityManager.persist(meeting);
            meetingSet.add(meeting);
        }
        subject.setMeetingSet(meetingSet);
        entityManager.persist(subject);
    }

    @Test
    public void testSaveMethod_WhenSaveTagFinance_ReturnMeeting(){
        //Give
        Meeting meetingEntity = mockMeeting();
        System.out.println(meetingEntity.getSubject().getSubjectName());

        //When
        entityManager.merge(meetingEntity);
        Meeting meeting = meetingRepository.save(meetingEntity);

        //Then
        assertEquals(meetingEntity.getId(), meeting.getId());
    }

    @Test
    public void testDeleteMeetingById_ReturnMeetingIsDeleted(){
        //Give
        Long subjectId = 1L;

        //When
        Integer i = meetingRepository.deleteAllBySubjectId(subjectId);

        //Then
        assertEquals(i, 10);
    }

    @Test
    public void findAllBySubjectId_ReturnMeetingList() {
        //Given
        Long subjectId = 1L;

        //When
        List<Meeting> subjectList = meetingRepository.findAllBySubjectId(subjectId);

        //Then
        assertFalse(subjectList.isEmpty());
        assertEquals(subjectList.size(), 10);
    }

    @Test
    public void findAllByTeacherId(){
        //Give
        String userId = "6656485";

        //When
        List<MeetingDTO> meetingList = meetingRepository.findAllByUserId(userId);

        //Then
        assertFalse(meetingList.isEmpty());
    }

    private Meeting mockMeeting(){
        return Meeting.builder()
                .subject(mockSubject())
                .build();
    }
    private Subject mockSubject(){
        return Subject.builder()
                .subjectName("TestCase")
                .build();
    }
}