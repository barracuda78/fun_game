package com.barracuda.fun.gui.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Direction {

  UP("up"),

  DOWN("down"),

  LEFT("left"),

  RIGHT("right");

  final String code;

}
