package com.vvh.coresv.service;

import com.vvh.coresv.dto.MeetingDTO;
import com.vvh.coresv.entity.Meeting;
import com.vvh.coresv.repository.MeetingRepository;
import com.vvh.coresv.response.meeting.MeetingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService{

    private final MeetingRepository meetingRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public MeetingServiceImpl(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public List<Meeting> findAllBySubjectId(Long subjectId) {
        return meetingRepository.findAllBySubjectId(subjectId);
    }

    @Override
    public List<MeetingResponse> findAllByUserId(String teacherId) {
        return meetingRepository.findAllByUserId(teacherId).stream().map(meetingDTO -> {
            return modelMapper.map(meetingDTO, MeetingResponse.class);
        }).toList();
    }
}
