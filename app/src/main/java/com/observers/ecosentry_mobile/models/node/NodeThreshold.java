package com.observers.ecosentry_mobile.models.node;

import androidx.core.content.ContextCompat;

import com.observers.ecosentry_mobile.R;

/**
 * Feature Engineering (Normalization Technique)
 * _ Min - Max Scaling (Convert Field's data into scale of 0 - 1)
 * _ Weighting System (Clarify the weighting factor on each features)
 * _ Threshold for each Fields (3 thresholds)
 * _ Calculate Average Score based on Normalized Fields (Danger Level) (2 thresholds)
 */

public abstract class NodeThreshold {

    // ======================================================
    // == Fields
    // ======================================================

    /**
     * Threshold level from left to right
     * --- Safe ---- Warning ---- Danger ----
     *
     * Source:
     * - National Fire Danger Rating System (NFDRS) in the United States:
     * https://www.fs.usda.gov/detail/cibola/landmanagement/resourcemanagement/?cid=stelprdb5368839
     *
     * - Canadian Forest Fire Danger Rating System (CFFDRS):
     * https://cwfis.cfs.nrcan.gc.ca/background/summary/fdr
     *
     * - Australian Forest Fire Danger Index (FFDI):
     * http://www.bom.gov.au/weather-services/fire-weather-centre/bushfire-weather/index.shtml
     */
    public static final double[][] featuresThreshold = {
            {15, 25, 35},    // temp,          'C
            {90, 70, 50},    // air humidity,   %
            {100, 60, 40},   // soil moisture,  %
            {350, 400, 500}, // co,             ppm
            {20, 50, 70},       // rain fall,      mm/h
            {20, 50, 50}     // dust particle,  micro gram/m^3
    };

    /**
     * Could be adjust based on your risk tolerance
     */
    static final double safeThreshold = 0.2;
    static final double warningThreshold = 0.7;

    /**
     * Indicate the important of the data
     * The sum of all weights should be 1
     * e.g. Temp is far more important than air humidity
     */
    private static final double[] weights = {0.5, 0.10, 0.2, 0.10, 0.05, 0.05};

    // ======================================================
    // == Danger Level Methods
    // ======================================================

    /**
     * Compute Danger Level based on normalized features (Using lowerbound and upperbound only)
     * Using Min-Max normalization
     *
     * @param node
     * @return danger level
     */
    protected static double computeDangerLevelData(Node node) {
        final double temperature = normalizeFeature(node.getTemperature(), featuresThreshold[0][0], featuresThreshold[0][2]);
        final double humidity = normalizeFeature(node.getHumidity(), featuresThreshold[0][0], featuresThreshold[0][2]);
        final double soil_moisture = normalizeFeature(node.getSoil_moisture(), featuresThreshold[0][0], featuresThreshold[0][2]);
        final double rain = normalizeFeature(node.getRain(), featuresThreshold[0][0], featuresThreshold[0][2]);
        final double co = normalizeFeature(node.getCo(), featuresThreshold[0][0], featuresThreshold[0][2]);
        final double dust = normalizeFeature(node.getDust(), featuresThreshold[0][0], featuresThreshold[0][2]);

        final double[] normalizedFeatures = {temperature, humidity, soil_moisture, rain, co, dust};

        // Calculate danger level using weighted average (easy approach)
        // 6 features
        double dangerScore = 0;
        for (int i = 0; i < normalizedFeatures.length; i++) {
            dangerScore += normalizedFeatures[i] * weights[i];
        }

        return dangerScore;
    }

    /**
     * Get Color based on risk tolerance (adjustable)
     *
     * @param data
     * @return Color based on threshold danger level
     */
    protected static int getColorOnThresholdDangerLevel(double data) {

        if (data <= safeThreshold) {
            return R.color.danger_level_safe;
        } else if (data <= warningThreshold) {
            return R.color.danger_level_warning;
        } else {
            return R.color.danger_level_danger;
        }
    }

    /**
     * Get Text based on risk tolerance (adjustable)
     *
     * @param data
     * @return text view based on risk tolerance
     */
    protected static int getTextOnThresholdDangerLevel(double data) {

        if (data <= safeThreshold) {
            return R.string.dashboard_danger_level_safe;
        } else if (data <= warningThreshold) {
            return R.string.dashboard_danger_level_warning;
        } else {
            return R.string.dashboard_danger_level_danger;
        }
    }

    // ======================================================
    // == Fields Methods
    // ======================================================

    /**
     * Using Min-Max Scaling (Normalization technique)
     *
     * @return convert unique feature into a scale of 0 - 1
     */
    protected static double normalizeFeature(double feature, double min, double max) {
        return (feature - min) / (max - min);
    }

    /**
     * Get Color based on 4 Indicator threshold
     * - Good
     * - Moderate
     * - High
     * - Hazardous
     *
     * @param data
     * @param th_1
     * @param th_2
     * @param th_3
     * @return a correspondent color band
     */
    protected static int getColorOnThresholdField(double data, double th_1, double th_2, double th_3) {
        if (data <= th_1) {
            return R.color.node_low;
        } else if (data <= th_2) {
            return R.color.node_moderate;
        } else if (data <= th_3) {
            return R.color.node_high;
        } else {
            return R.color.node_hazardous;
        }
    }
}
