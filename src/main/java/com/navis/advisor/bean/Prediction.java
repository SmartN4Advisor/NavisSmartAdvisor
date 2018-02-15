package com.navis.advisor.bean;

/**
 * This POJO represents the prediction response from scikit-learn app
 */
public class Prediction {
    String prediction;

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}
