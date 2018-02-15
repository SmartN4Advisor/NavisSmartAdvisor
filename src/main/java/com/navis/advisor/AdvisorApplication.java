package com.navis.advisor;

import com.navis.advisor.bean.N4HealthLog;
import com.navis.advisor.bean.QueueLog;
import com.navis.advisor.bean.TopicLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AdvisorApplication {

	private List<N4HealthLog> logEntries = new ArrayList() {
		@Override
		public boolean add(Object o) {
			if (size() == 10) {
				this.remove(0);
			}
			return super.add(o);
		}
	};

	@Bean
	public List<N4HealthLog> n4LogEntries() {
		List<QueueLog> queueLogs = new ArrayList<>();
		queueLogs.add(new QueueLog() {
			{
				setQueueName("esbAggregateClusterQueue");
				setQueueSize(100L);
				setInFlight(1L);
				setEnqRate(10L);
				setDeqRate(10L);
				setConsumerCount(2L);
			}
		});
		List<TopicLog> topicLogs = new ArrayList<>();
		topicLogs.add(new TopicLog() {
			{
				setHostName("INMAA3-VN4QA402");
				setQueueSize(100L);
			}
		});
		logEntries.add(new N4HealthLog() {
			{
				setCpu(30L);
				setFreeMemory(7033L);
				setAmqMemoryPercentUsage(0L);
				setAmqStorePercentUsage(0L);
				setQueueLogs(queueLogs);
				setTopicLogs(topicLogs);
			}
		});
		return logEntries;
	}

	public static void main(String[] args) {
		SpringApplication.run(AdvisorApplication.class, args);
	}
}
