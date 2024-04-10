package com.barracuda.fun;

import com.barracuda.fun.gui.WindowFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FunApplication {

//	public static void main(String[] args) {
//			SpringApplication.run(FunApplication.class, args);
//	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(FunApplication.class)
			.headless(false).run(args);
		context.getBean(WindowFrame.class);
	}

}
