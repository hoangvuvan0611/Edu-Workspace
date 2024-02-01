package com.vvh.coresv.repository;

import com.vvh.coresv.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void init(){
        for(int i=0; i<10; i++){
            Student student = Student.builder()
                    .id(String.valueOf(i))
                    .firstName("Student " + i)
                    .build();
            entityManager.persistAndFlush(student);
        }
    }

    @Test
    void testMethodSave_WhenSaveStudent_ReturnStudent(){
        //Give
        Student studentEntity = mockStudent();

        //When
        entityManager.persistAndFlush(studentEntity);
        Student student = studentRepository.save(studentEntity);

        //Then
        assertEquals(studentEntity.getId(), student.getId());
    }

    @Test
    void findById() {
        //Give
        String studentId = "1";

        //When
        Optional<Student> student = studentRepository.findById(studentId);

        //Then
        assertTrue(student.isPresent());
    }

    private Student mockStudent(){
        return Student.builder()
                .id("11")
                .firstName("Student 11")
                .build();
    }
}