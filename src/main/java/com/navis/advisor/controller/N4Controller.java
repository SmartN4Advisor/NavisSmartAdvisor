package com.navis.advisor.controller;

import com.navis.advisor.bean.N4HealthLog;
import com.navis.advisor.bean.QueueLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class N4Controller {
    Map<String, String> latestPrediction;

    // wired from the bean in AdvisorApplication
    @Autowired
    List<N4HealthLog> logEntries;

    /**
     * Root, to test if the service is alive
     *
     * http://localhost:8080
     *
     * @return String0
     */
    @GetMapping("/")
    public String root() {
        return "Hello.  This service is alive!";
    }

    /**
     * Invoked by the N4 center node to push the log entry once every 30 seconds
     *
     * @param inHealthLog
     * @return
     */
    @RequestMapping(value = "/n4log", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> pushLogEntry(@RequestBody N4HealthLog inHealthLog) {

        System.out.println("Received from N4 Center Node: " + inHealthLog);
        logEntries.add(inHealthLog);
        float totalExpectedConsumerCount = 2;

        final QueueLog queueLog = inHealthLog.getQueueLogs()
                .stream()
                .filter(q -> q.getQueueName().startsWith("bridge."))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Did not find bridge queue!"));
        // Invoke Scikit-learn http entry point.  Getting a prediction may take many seconds.  Let's get the
        // prediction right now before Alexa/android request for it.
        String predictionUrl = "https://navis-sklearn-advisor.herokuapp.com/get_node_health_advice?" +
                "f1=" + inHealthLog.getCpu() +
                "&f2=" + (inHealthLog.getFreeMemory() / (float) inHealthLog.getTotalMemory()) +
                "&f3=" + ((queueLog.getQueueSize() > 2000) ? 1 : 0) +
                "&f4=" + (queueLog.getConsumerCount() / totalExpectedConsumerCount) +
                "&f5=" + (queueLog.getDeqRate() >= queueLog.getEnqRate() ? 1 : 0) +
                "&f6=" + queueLog.getInFlight() / totalExpectedConsumerCount*2;
        System.out.println("URL: " + predictionUrl);
        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Prediction> response = restTemplate.getForEntity(predictionUrl, Prediction.class);
//        latestPrediction = response.getBody();
        ResponseEntity<Map> response = restTemplate.getForEntity(predictionUrl, Map.class);
        latestPrediction = response.getBody();
        System.out.println("Response from ML: " + response.getBody());
        System.out.println(response.getStatusCode());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Invoked by Android to get a list of log entries
     *
     * http://localhost:8080/getLogEntries
     *
     * @return  List of log entries
     */
    @GetMapping("/getLogEntries")
    public List<N4HealthLog> getLogEntries() {
        // TODO can do data pre-processing here before returning to Android
        return logEntries;
    }

    /**
     * Invoked by Alexa (and Android) to get the current system status
     *
     * http://localhost:8080/getHealthStatus
     *
     * @return List of strings
     */
    @GetMapping("/getHealthStatus")
    public List<String> getHealthStatus() {
        // TODO prepare current status about the latest CPU load, Memory, queue Size, etc
        N4HealthLog log = logEntries.get(logEntries.size()-1);
        return Arrays.asList("CPU is " + log.getCpu() + "%",
                "Free Memory is " + log.getFreeMemory() + " GB",
                "ActiveMQ Memory Percentage is " + log.getAmqMemoryPercentUsage());
    }

    /**
     * Invoked by Alexa (and Android) to get a smart recommendation obtained from machine-learning app
     *
     * http://localhost:8080/getRecommendation
     *
     * @return
     */
    @GetMapping("/getRecommendation")
    public String getRecommendation() {
        // Quick response by returning the latest calculated prediction.
        if ("Good".compareToIgnoreCase(latestPrediction.get("SystemHealthAdvisor"))==0 &&
                "Good".compareToIgnoreCase(latestPrediction.get("QueueHealthAdvisor"))==0) {
            return "N4 is currently running and stable";
        }
        return "The result of the system analysis is: " +  latestPrediction.get("SystemHealthAdvisor") +
                ".  The result of the queue analysis is: " + latestPrediction.get("QueueHealthAdvisor") + ".";
    }

}
