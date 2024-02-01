package com.vvh.coresv.service;

import com.vvh.coresv.entity.Student;
import com.vvh.coresv.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> findById(String id) {
        return studentRepository.findById(id);
    }
}
