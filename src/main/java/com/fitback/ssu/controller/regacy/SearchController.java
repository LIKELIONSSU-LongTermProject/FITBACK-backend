//package com.fitback.ssu.controller.regacy;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value = "/search")
//public class SearchController {
//    /**
//     *
//     * @param part 1. DEV_FRONT 2. DEV_BACK 3. DESIGN 4. PM
//     * @param sort 1. new 2. deadline
//     * @return
//     */
//    @GetMapping(value = "/part")
//    public void searchByPart(
//            @RequestParam(value = "pname",required = false,defaultValue = "") String part,
//            @RequestParam(value = "sort",required = false,defaultValue = "") String sort){
//
//    }
//
//    /**
//     *
//     * @param keyword
//     * @param sort 1. new 2. deadline
//     */
//    @GetMapping(value = "/keyword")
//    public void searchByKeyword(
//            @RequestParam(value = "kname",required = false,defaultValue = "") String keyword,
//            @RequestParam(value = "sort",required = false,defaultValue = "") String sort){
//
//    }
//}
