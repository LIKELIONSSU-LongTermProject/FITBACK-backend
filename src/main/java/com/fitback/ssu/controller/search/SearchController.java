package com.fitback.ssu.controller.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/search")
public class SearchController {
    /**
     *
     * @param part 1. DEV_FRONT 2. DEV_BACK 3. DESIGN 4. PM
     * @param keyword 키워드
     * @param sort 1. new 2. deadline 3. save
     * @return
     */
    @GetMapping(value = "")
    public void searchTab(
            @RequestParam(value = "part",required = false,defaultValue = "") String part,
            @RequestParam(value = "keyword",required = false,defaultValue = "") String keyword,
            @RequestParam(value = "sort",required = false,defaultValue = "") String sort){

    }
}
