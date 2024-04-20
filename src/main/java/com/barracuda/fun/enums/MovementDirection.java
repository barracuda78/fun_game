package com.barracuda.fun.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MovementDirection {

    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right");

    private final String code;

}
