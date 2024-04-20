package com.barracuda.fun.gui.entity.draw_handler;

import lombok.NonNull;

public interface DrawEntityDirectionHandlerRegistry {

    DrawEntityDirectionHandler getHandler(@NonNull String direction);

}
