package com.vvh.coresv.controller;

import com.vvh.coresv.dto.MeetingDTO;
import com.vvh.coresv.entity.Meeting;
import com.vvh.coresv.request.CreateMeetingRequest;
import com.vvh.coresv.response.base.BaseResponseListItem;
import com.vvh.coresv.response.meeting.MeetingResponse;
import com.vvh.coresv.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/core/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewMeeting(@RequestBody CreateMeetingRequest request){
        
        return null;
    }

    @GetMapping("/subjectId={subjectId}")
    public BaseResponseListItem<Meeting> findAllBySubjectId(@PathVariable String subjectId){
        BaseResponseListItem<Meeting> baseResponseListItem = new BaseResponseListItem<>();
        List<Meeting> meetingList = meetingService.findAllBySubjectId(Long.parseLong(subjectId));
        baseResponseListItem.setResult(meetingList.size(), meetingList);
        return baseResponseListItem;
    }

    @GetMapping("/teacherId={teacherId}")
    public BaseResponseListItem<MeetingResponse> findAllByTeacherId(@PathVariable String teacherId){
        BaseResponseListItem<MeetingResponse> baseResponseListItem = new BaseResponseListItem<>();
        List<MeetingResponse> meetingList = meetingService.findAllByUserId(teacherId);
        baseResponseListItem.setResult(meetingList.size(), meetingList);
        return baseResponseListItem;
    }
}
