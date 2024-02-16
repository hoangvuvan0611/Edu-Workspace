package com.vvh.coresv.controller;

import com.vvh.coresv.response.base.BaseResponse;
import com.vvh.coresv.response.base.BaseResponseError;
import com.vvh.coresv.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/core/scraper")
public class ScraperController {
    private final ScraperService scraperService;

    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("{code}")
    public ResponseEntity<?> scrapingSchedule(@PathVariable String code){
        log.info(code);
        boolean success = scraperService.scrapingData(code);
        if(success){
            return ResponseEntity.ok(new BaseResponse(true));
        }else {
            return ResponseEntity.ok(new BaseResponseError());
        }
    }
}
