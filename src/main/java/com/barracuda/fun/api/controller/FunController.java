package com.barracuda.fun.api.controller;

import com.barracuda.fun.api.dto.FunDto;
import com.barracuda.fun.domain.entity.Fun;
import com.barracuda.fun.service.FunService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FunController {

    private final FunService funService;

    @GetMapping("/v1/funs")
    public List<Fun> getFun() {
        final List<Fun> allFun = funService.getAllFun();
        return allFun;
    }

    @PostMapping(path = "v1/funs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer createNewFun(@RequestBody @Valid FunDto funDto) {
        return funService.createNewFun(funDto);
    }

}
