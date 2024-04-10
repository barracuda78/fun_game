package com.barracuda.fun.service;

import com.barracuda.fun.api.dto.FunDto;
import com.barracuda.fun.domain.entity.Fun;
import java.util.List;
import lombok.NonNull;

public interface FunService {

    List<Fun> getAllFun();

    Integer createNewFun(@NonNull FunDto funDto);

}

