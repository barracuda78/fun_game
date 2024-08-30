package com.barracuda.fun.service;

import com.barracuda.fun.api.dto.FunDto;
import com.barracuda.fun.domain.entity.Fun;
import com.barracuda.fun.repository.FunRepository;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FunServiceImpl implements FunService {

    private final FunRepository funRepository;

    @Override
    public List<Fun> getAllFun() {
        return funRepository.findAll();
    }

    @Override
    public Integer createNewFun(@NonNull FunDto funDto) {
        final Fun fun = Fun.builder()
            .name(funDto.getName())
            .isActive(funDto.getIsActive())
            .build();
        Fun savedFun = funRepository.save(fun);
        return savedFun.getId();
    }

}
