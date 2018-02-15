package com.navis.advisor.bean;

/**
 * This POJO represents the prediction response from scikit-learn app
 */
public class Prediction {
    String queueHealthAdvisor;
    String systemHealthAdvisor;

    public String getQueueHealthAdvisor() {
        return queueHealthAdvisor;
    }

    public void setQueueHealthAdvisor(String queueHealthAdvisor) {
        this.queueHealthAdvisor = queueHealthAdvisor;
    }

    public String getSystemHealthAdvisor() {
        return systemHealthAdvisor;
    }

    public void setSystemHealthAdvisor(String systemHealthAdvisor) {
        this.systemHealthAdvisor = systemHealthAdvisor;
    }
}
