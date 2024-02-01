package com.vvh.coresv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "credit_num")
    private Byte creditNum;

    @Column(name = "class_code")
    private String classCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    public Set<Meeting> meetingSet = new HashSet<>();

    @ManyToMany()
    @JoinTable(
            name = "subjects_students",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    public List<Student> studentList = new ArrayList<>();
}
