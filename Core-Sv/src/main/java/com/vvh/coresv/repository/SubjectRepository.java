package com.vvh.coresv.repository;

import com.vvh.coresv.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findBySubjectCode(String subjectCode);
    List<Subject> findAllByUserId(Long user_id);
    @Modifying
    @Transactional
    Integer deleteAllByUserId(Long user_id);
}
