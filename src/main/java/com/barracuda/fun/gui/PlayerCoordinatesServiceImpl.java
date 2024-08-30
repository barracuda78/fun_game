package com.barracuda.fun.gui;

import com.barracuda.fun.gui.entity.Player;
import java.awt.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerCoordinatesServiceImpl implements PlayerCoordinatesService {

  private final Player player;

  @Override
  public Point getPlayerCoordinates() {
    return player.getCoordinates();
  }

}
