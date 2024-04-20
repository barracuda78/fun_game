package com.barracuda.fun.gui.entity.draw_handler;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrawEntityDirectionHandlerRegistryImpl implements DrawEntityDirectionHandlerRegistry {

    private final List<DrawEntityDirectionHandler> handlers;

    public DrawEntityDirectionHandler getHandler(@NonNull String direction) {
        return handlers.stream()
            .filter(handler -> handler.canHandle(direction))
            .findFirst()
            .orElse(new DrawEntityDirectionHandlerDefault());
    }

}
