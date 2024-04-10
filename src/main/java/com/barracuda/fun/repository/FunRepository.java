package com.barracuda.fun.repository;

import com.barracuda.fun.domain.entity.Fun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunRepository  extends JpaRepository<Fun, Integer> {

}
