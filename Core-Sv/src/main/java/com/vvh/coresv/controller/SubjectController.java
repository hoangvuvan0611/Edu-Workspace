package com.vvh.coresv.controller;

import com.vvh.coresv.dto.response.ResponseData;
import com.vvh.coresv.dto.response.ResponseError;
import com.vvh.coresv.entity.Subject;
import com.vvh.coresv.response.base.BaseResponseListItem;
import com.vvh.coresv.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/core/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Operation(summary = "Get all subject of user", description = "Send a request via this api to get all subject of userId")
    @GetMapping({"/{userId}"})
    public BaseResponseListItem<Subject> getAll(@PathVariable String userId){
        List<Subject> subjectList = subjectService.getAll(userId);

        log.info(subjectList.toString());
        BaseResponseListItem<Subject> responseListItem = new BaseResponseListItem<>();
        responseListItem.setResult(subjectList.size(), subjectList);
        return responseListItem;
    }

    @Operation(summary = "Delete subjects by userId", description = "Send a request via this api to delete all subject of userId")
    @DeleteMapping("/deleteBy/{userId}")
    public ResponseData<?> deleteSubjectsByUserId(@PathVariable Long userId){
        log.info("Delete subjects by user id: {}", userId);
        try {
            subjectService.deleteSubjectsByUserId(userId);
            return new ResponseData<>(
                    HttpStatus.NO_CONTENT.value(),
                    "Delete subjects by user id " + userId + " successfully",
                    null
            );
        } catch (Exception ex) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
    }
}
