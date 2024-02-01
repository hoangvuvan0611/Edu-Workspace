package com.vvh.coresv.service;

import com.vvh.coresv.entity.Subject;
import com.vvh.coresv.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> getAll(String teacherId) {
        return subjectRepository.findAll();
    }

    @Override
    public void addSubject(Subject subject) {

    }

    @Override
    public void findSubject(String subjectCode, String teacherCode) {

    }

}
