package com.vvh.coresv.entity;

import com.vvh.coresv.model.LocalAddress;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sheet")
public class AttendanceSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "subject_id")
    private String subjectId;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "name")
    private String name;

    @Column(name = "distance")
    private Double distance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
