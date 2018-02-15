package com.navis.advisor.bean;

/**
 * This POJO represents the section in the log entry about the AMQ queues
 */
public class QueueLog {
    private String queueName;
    private Long queueSize;
    private Long inFlight;
    private Long enqRate;
    private Long deqRate;
    private Long consumerCount;

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Long getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Long queueSize) {
        this.queueSize = queueSize;
    }

    public Long getInFlight() {
        return inFlight;
    }

    public void setInFlight(Long inFlight) {
        this.inFlight = inFlight;
    }

    public Long getEnqRate() {
        return enqRate;
    }

    public void setEnqRate(Long enqRate) {
        this.enqRate = enqRate;
    }

    public Long getDeqRate() {
        return deqRate;
    }

    public void setDeqRate(Long deqRate) {
        this.deqRate = deqRate;
    }

    public Long getConsumerCount() {
        return consumerCount;
    }

    public void setConsumerCount(Long consumerCount) {
        this.consumerCount = consumerCount;
    }

    @Override
    public String toString() {
        return "{ queueName:" + queueName + " queueSize:" + queueSize + " inFlight:" + inFlight +
                " enqRate:" + enqRate + " deqRate:" + deqRate + " consumerCount:" + consumerCount + " }";
    }
}
