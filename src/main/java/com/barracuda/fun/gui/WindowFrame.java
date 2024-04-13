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

    @PostConstruct
    public void init() {
        setSize(300,300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("fun game");
        this.add(gamePanel);
        this.pack();
        this.setLocationRelativeTo(null);

//        JButton button = new JButton("Press");
//        getContentPane().add(button);
        this.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

//    public static void main(String[] args) {
//        KeyHandler keyHandler = new KeyHandler();
//        GamePanel gamePanel = new GamePanel(keyHandler);
//        WindowFrame windowFrame = new WindowFrame(gamePanel);
//        windowFrame.init();
//    }

}
