package com.barracuda.fun.gui;

import jakarta.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WindowFrame extends JFrame {

    private final GamePanel gamePanel;

    private final DrawServiceImpl drawService;

    @PostConstruct
    public void init() {
        setSize(300,300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("fun game");
        this.add(drawService);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        gamePanel.startGameThread();
    }

}
