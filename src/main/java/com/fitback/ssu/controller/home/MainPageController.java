package com.fitback.ssu.controller.home;

import com.fitback.ssu.dto.home.ProCardDto;
import com.fitback.ssu.dto.home.ProProfileDto;
import com.fitback.ssu.service.home.ProListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/main")
public class MainPageController {

    private final ProListService proListService;

    // 로그인한 유저에 따라 추천
    @GetMapping(value = "/recommendation")
    public ArrayList<ProProfileDto> recommandProsByUser(){
        return proListService.recommendProsByUserData();
    }

    // pro 검색
    @GetMapping(value = "/search")
    public List<List<ProProfileDto>> searchPro(
            @RequestParam(value = "company",required = false,defaultValue = "") String company,
            @RequestParam(value = "duty",required = false,defaultValue = "") String duty,
            @RequestParam(value = "annual",required = false, defaultValue = "0") Integer annual,
            @RequestParam(value = "sort",required = false, defaultValue = "rnum") String sort){
        return proListService.searchPro(company, duty, annual, sort);
    }

    // pro info 하나 눌렀을 때
    @GetMapping(value = "/proinfo")
    public ProCardDto getProCard(@RequestParam Long pid){
        return proListService.getProCard(pid);
    }
}
