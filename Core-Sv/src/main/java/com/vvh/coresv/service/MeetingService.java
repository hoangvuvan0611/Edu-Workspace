package com.vvh.coresv.service;

import com.vvh.coresv.entity.Meeting;
import com.vvh.coresv.response.meeting.MeetingResponse;

import java.util.List;

public interface MeetingService {
    List<Meeting> findAllBySubjectId(Long subjectId);
    List<MeetingResponse> findAllByUserId(String teacherCode);
}
