package com.vvh.coresv.repository;

import com.vvh.coresv.dto.model.MeetingDTO;
import com.vvh.coresv.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, String> {

    @Query(value = "select m from Meeting m where m.subject.id = :subjectId")
    List<Meeting> findAllBySubjectId(@Param("subjectId") Long subjectId);

    Integer deleteAllBySubjectId(Long subjectId);

    @Query(value = "select new com.vvh.coresv.dto.model.MeetingDTO" +
            "(m.id, m.roomName, m.startEndTime, m.meetingNote , s.subjectName) " +
            "from Meeting m " +
            "join Subject s on s.id = m.subject.id " +
            "join User u on u.id = s.user.id " +
            "where u.code = :code")
    List<MeetingDTO> findAllByUserId(String code);
}
