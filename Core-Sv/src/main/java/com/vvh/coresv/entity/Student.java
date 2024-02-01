package com.vvh.coresv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Student")
public class Student {
    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_code")
    private String classCode;

    @Column(name = "class_name")
    private String className;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "major")
    private String major;

    @Column(name = "department")
    private String department;

    @JsonIgnore
    @ManyToMany(mappedBy = "studentList")
    private List<Subject> subjectList = new ArrayList<>();
}
