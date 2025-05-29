package com.example.HDSGTest.Config;

import jakarta.annotation.PostConstruct;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.global.opencv_core;
import org.springframework.stereotype.Component;

@Component
public class OpenCVConfig {
    @PostConstruct
    public void init() {
        try {
            Loader.load(opencv_core.class);
            System.out.println("OpenCV loaded successfully!");
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load OpenCV", e);
        }
    }
}