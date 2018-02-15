package com.navis.advisor.controller;

import com.navis.advisor.bean.N4HealthLog;
import com.navis.advisor.bean.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class N4Controller {
    Prediction latestPrediction;

    // wired from the bean in AdvisorApplication
    @Autowired
    List<N4HealthLog> logEntries;

    /**
     * Root, to test if the service is alive
     *
     * http://localhost:8080
     *
     * @return String
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

        // Invoke Scikit-learn http entry point.  Getting a prediction may take many seconds.  Let's get the
        // prediction right now before Alexa/android request for it.
        String predictionUrl = "https://navis-sklearn-advisor.herokuapp.com/get_node_health_advice?f1=0&f2=0.8&f3=0&f4=0.5&f5=0.5&f6=0.5";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(predictionUrl, String.class);
        latestPrediction = new Prediction();
        latestPrediction.setPrediction(response.getBody());
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
        return Arrays.asList("CPU is 10%", "Memory is 1 GB", "No queue sizes");
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
        if (latestPrediction == null) {
            latestPrediction = new Prediction();
            latestPrediction.setPrediction("Please restart the center node.");
        }
        return latestPrediction.getPrediction();
    }

}
