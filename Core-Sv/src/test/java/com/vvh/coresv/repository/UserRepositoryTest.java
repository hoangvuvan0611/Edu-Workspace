package com.vvh.coresv.repository;

import com.vvh.coresv.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init(){
        for(int i=0; i<10; i++){
            User user = User.builder()
                    .id((long) i)
                    .userName("Teacher " + i)
                    .build();
            entityManager.persistAndFlush(user);
        }
    }

    @Test
    void findById() {
        //Give
        String studentId = "1";

        //When
        Optional<User> teacher = userRepository.findById(studentId);

        //Then
        assertTrue(teacher.isPresent());
    }

    @Test
    void testMethodSave_WhenSaveTeacher_ReturnTeacher(){
        //Give
        User userEntity = mockTeacher();

        //When
        entityManager.persistAndFlush(userEntity);
        User user = userRepository.save(userEntity);

        //Then
        assertEquals(userEntity.getId(), user.getId());
        assertEquals(user.getUserName(), "Teacher 11");
    }

    private User mockTeacher(){
        return User.builder()
                .id(11L)
                .userName("Teacher 11")
                .build();
    }
}