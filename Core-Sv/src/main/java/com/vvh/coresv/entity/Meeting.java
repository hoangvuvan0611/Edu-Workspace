package com.vvh.coresv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "start_end_time")
    private List<Date> startEndTime;

    @Column(name = "meeting_note")
    private String meetingNote;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meeting")
    private Set<AttendanceSheet> attendanceSheetSet = new HashSet<>();
}
