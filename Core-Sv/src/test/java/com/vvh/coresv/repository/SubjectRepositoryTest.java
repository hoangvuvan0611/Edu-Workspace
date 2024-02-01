package com.vvh.coresv.repository;

import com.vvh.coresv.entity.Subject;
import com.vvh.coresv.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class SubjectRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    SubjectRepository subjectRepository;

    @BeforeEach
    void init(){
        User user = User.builder()
                .id("1")
                .name("Teacher")
                .build();
        Set<Subject> subjectSet = new HashSet<>();
        for(int i=0; i<10; i++){
            Subject subject = Subject.builder()
                    .subjectName("subject "  + i)
                    .subjectCode(String.valueOf(i))
                    .user(user)
                    .build();
            entityManager.persist(subject);
            subjectSet.add(subject);
        }
        user.setSubjectSet(subjectSet);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    void testSaveMethod_WhenSaveSubject_ReturnSubject(){
        //Give
        Subject subjectEntity = mockSubject();

        //When
        entityManager.persistAndFlush(subjectEntity);
        Subject subject = subjectRepository.save(subjectEntity);

        //Then
        assertEquals(subjectEntity.getId(), subject.getId());
    }

    @Test
    void findBySubjectCode() {
        //Give
        String subjectCode = "1";

        //When
        Optional<Subject> subject = subjectRepository.findBySubjectCode(subjectCode);

        //Then
        assertTrue(subject.isPresent());
    }

    @Test
    void deleteAllByTeacher() {
        //Give
        String teacherId = "1";

        //When
        Integer i = subjectRepository.deleteAllByUserId(teacherId);

        //Then
        assertEquals(i, 10);
    }

    @Test
    void findAllByTeacherId(){
        //Give
        String teacherId = "1";

        //When
        List<Subject> subjectList = subjectRepository.findAllByUserId(teacherId);

        //Then
        assertFalse(subjectList.isEmpty());
        assertEquals(subjectList.size(), 10);
    }

    private Subject mockSubject(){
        return Subject.builder()
                .subjectName("MockTeacher")
                .user(mockTeacher())
                .build();
    }

    private User mockTeacher(){
        return User.builder()
                .id("2")
                .name("MockTeacher")
                .build();
    }
}