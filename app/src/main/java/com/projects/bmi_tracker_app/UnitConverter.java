package com.projects.bmi_tracker_app;

public class UnitConverter {
    public static double convertHeightToMeters(double height, String unit) {
        switch (unit) {
            case "cm":
                return height / 100.0;
            case "m":
                return height;
            case "inches":
                return height * 0.0254;
            case "feet":
                return height * 0.3048;
            default:
                return 0.0;
        }
    }

    public static double convertWeightToKg(double weight, String unit) {
        switch (unit) {
            case "kg":
                return weight;
            case "lbs":
                return weight * 0.453592;
            case "stone":
                return weight * 6.35029;
            default:
                return 0.0;
        }
    }
}
