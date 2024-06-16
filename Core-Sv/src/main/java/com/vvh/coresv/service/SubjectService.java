package com.vvh.coresv.service;

import com.vvh.coresv.entity.Subject;

import java.util.List;

public interface SubjectService {
    public List<Subject> getAll(String userId);
    public void addSubject(Subject subject);
    public void findSubject(String subjectCode, String teacherCode);
    public void deleteSubjectsByUserId(Long userId);
}
