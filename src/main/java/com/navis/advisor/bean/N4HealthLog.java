package com.navis.advisor.bean;

import java.util.List;

/**
 * This POJO represents the log entry is being pushed from the N4 center node.  Below is an
 * example of such log.  The first line says about the JVM itself.  The following lines are
 * about the AMQ queues.  The last few (optional) lines are about the AMQ topic consumers.
 * They are optional because topic consumers may be absent.
 *
 CPU=17%  TotalMem=8192 MB  FreeMem=7033 MB  AMQMemoryPercentUsage=0%  AMQStorePercentUsage=0%
 a4.agv_status size=0 inFlight=0 enqRate=1 deqRate=1
 n4.0.DPW/DPWA/AGW/AGW size=0 inFlight=0 enqRate=16 deqRate=16
 n4.tasks.1.DPW/DPWA/AGW/AGW size=0 inFlight=0 enqRate=16 deqRate=16
 bridge.DPW/DPWA/AGW/AGW size=2 inFlight=2 enqRate=7 deqRate=7
 n4.tasks.0.DPW/DPWA/AGW/AGW size=0 inFlight=0 enqRate=16 deqRate=16
 esbAggregateClusterQueue size=0 inFlight=0 enqRate=0 deqRate=0
 a4.agv_commands size=0 inFlight=0 enqRate=2 deqRate=2
 ActiveMQ.DLQ size=0 inFlight=0 enqRate=0 deqRate=0
 a4.asc_status size=0 inFlight=0 enqRate=0 deqRate=0
 bridge.* size=0 inFlight=0 enqRate=0 deqRate=0
 n4.1.DPW/DPWA/AGW/AGW size=0 inFlight=0 enqRate=17 deqRate=17
 a4.qc_wq_status_refresh size=0 inFlight=0 enqRate=0 deqRate=0
 a4.* size=0 inFlight=0 enqRate=0 deqRate=0
 esbBroadcastTopic ID:INMAA3-VN4QA402-56409-1517249569098-12:11 size=1 isSlow=true
 esbBroadcastTopic ID:INMAA3-VN4QA413-51673-1517249874596-12:9 size=2 isSlow=true
 esbBroadcastTopic ID:INMAA3-VN4QA415-53984-1517249885350-12:9 size=1 isSlow=true
 esbBroadcastTopic ID:INMAA1-VN4QA357-63965-1517250079210-12:5 size=2 isSlow=true
 esbBroadcastTopic ID:INMAA3-VN4QA435-62733-1517250080718-12:7 size=37 isSlow=true
 *
 */
public class N4HealthLog {
    private Long cpu;
    private Long totalMemory;
    private Long freeMemory;
    private Long amqMemoryPercentUsage;
    private Long amqStorePercentUsage;
    private List<QueueLog> queueLogs; // List of queue info
    private List<TopicLog> topicLogs; // List of topic consumers

    public Long getCpu() {
        return cpu;
    }

    public void setCpu(Long cpu) {
        this.cpu = cpu;
    }

    public Long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public Long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(Long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public Long getAmqMemoryPercentUsage() {
        return amqMemoryPercentUsage;
    }

    public void setAmqMemoryPercentUsage(Long amqMemoryPercentUsage) {
        this.amqMemoryPercentUsage = amqMemoryPercentUsage;
    }

    public Long getAmqStorePercentUsage() {
        return amqStorePercentUsage;
    }

    public void setAmqStorePercentUsage(Long amqStorePercentUsage) {
        this.amqStorePercentUsage = amqStorePercentUsage;
    }

    public List<QueueLog> getQueueLogs() {
        return queueLogs;
    }

    public void setQueueLogs(List<QueueLog> queueLogs) {
        this.queueLogs = queueLogs;
    }

    public List<TopicLog> getTopicLogs() {
        return topicLogs;
    }

    public void setTopicLogs(List<TopicLog> topicLogs) {
        this.topicLogs = topicLogs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CPU: " + cpu)
                .append(" FreeMemory: " + freeMemory)
                .append(" amqMemoryPercentUsage: " + amqMemoryPercentUsage)
                .append(" amqStorePercentUsage: " + amqStorePercentUsage)
                .append(" ");
        if (queueLogs != null && !queueLogs.isEmpty()) {
            queueLogs.forEach(elm -> {
                sb.append(elm.toString()).append(" ");
            });
        }
        return sb.toString();
    }
}
