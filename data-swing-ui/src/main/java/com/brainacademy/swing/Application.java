package com.brainacademy.swing;


import com.brainacademy.data.config.DatabaseConfig;
import com.brainacademy.swing.ui.MainFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import javax.swing.SwingUtilities;

@SpringBootApplication
@ComponentScan(basePackages = {"com.brainacademy.data", "com.brainacademy.swing"})
@Import(DatabaseConfig.class)
public class Application
        implements CommandLineRunner {

    @Autowired
    private MainFrame mainFrame;

    @Override
    public void run(String... args)
            throws Exception {
        SwingUtilities.invokeLater(() -> {
            mainFrame.showForm();
        });
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .headless(false)
                .build()
                .run(args);
    }
}
