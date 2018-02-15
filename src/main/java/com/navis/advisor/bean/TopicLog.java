package com.navis.advisor.bean;

/**
 * This POJO represents the section of log entry about the AMQ topic consumers
 */
public class TopicLog {
    private String hostName;
    private Long queueSize;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Long getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Long queueSize) {
        this.queueSize = queueSize;
    }
}
