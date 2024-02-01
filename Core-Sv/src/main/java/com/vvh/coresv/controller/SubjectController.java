package com.vvh.coresv.controller;

import com.vvh.coresv.entity.Subject;
import com.vvh.coresv.response.base.BaseResponseListItem;
import com.vvh.coresv.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/core/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping({"/{code}"})
    public BaseResponseListItem<Subject> getAll(@PathVariable String code){
        List<Subject> subjectList = subjectService.getAll(code);

        log.info(subjectList.toString());
        BaseResponseListItem<Subject> responseListItem = new BaseResponseListItem<>();
        responseListItem.setResult(subjectList.size(), subjectList);
        return responseListItem;
    }
}
