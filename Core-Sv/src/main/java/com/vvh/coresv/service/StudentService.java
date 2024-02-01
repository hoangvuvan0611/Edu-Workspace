package com.vvh.coresv.service;

import com.vvh.coresv.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<Student> findById(String id);
}
