package com.vvh.coresv.response.meeting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingResponse {
    private String id;
    private String roomName;
    private List<String> startEndTime;
    private String meetingNote;
    private String subjectName;
}
