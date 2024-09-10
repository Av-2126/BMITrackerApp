package com.projects.bmi_tracker_app;

public class BMIUtil {
    public static double calculateBMI(double heightInMeters, double weightInKg) {
        return weightInKg / (heightInMeters * heightInMeters);
    }
}
