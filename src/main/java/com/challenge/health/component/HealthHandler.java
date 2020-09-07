package com.challenge.health.component;

import com.challenge.health.model.Health;
import com.challenge.health.model.InfoRequest;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class HealthHandler {

    private final MeterRegistry meterRegistry;
    private Counter count_total_count_api_calls;
    private Counter count_total_request;
    private Counter count_error;

    private List<Long> avg_response_time;
    private List<Long> avg_response_time_api_calls;
    private List<InfoRequest> infoRequests;
    private Map<Integer, Counter> mapInfRequest;

    public HealthHandler(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        init();
    }

    private void init() {
        count_total_request = Counter.builder("count_total_request")
                .tag("count_total_request", "0")
                .register(meterRegistry);

        count_total_count_api_calls = Counter.builder("count_total_count_api_calls")
                .tag("count_total_count_api_calls", "0")
                .register(meterRegistry);

        avg_response_time = new ArrayList<>();
        avg_response_time_api_calls = new ArrayList<>();
        infoRequests = new ArrayList<>();
        mapInfRequest = new HashMap<>();

        count_error = Counter.builder("count_error")
                .tag("count_error", "0")
                .register(meterRegistry);
    }

    public void register(Long time) {
        Long total = (System.currentTimeMillis() - time) / 1000;
        avg_response_time.add(total);

        count_total_request.increment(1.0);
    }

    public void registerError(Integer errorCode) {
        if (mapInfRequest.containsKey(errorCode)) {
            mapInfRequest.get(errorCode).increment(1.0);
        } else {
            mapInfRequest.put(errorCode, Counter.builder("count_error")
                    .tag("count_error", "0")
                    .register(meterRegistry));
        }

    }

    public void registerExternalCall(Long time) {
        Long total = (System.currentTimeMillis() - time) / 1000;
        avg_response_time_api_calls.add(total);

        count_total_count_api_calls.increment(1.0);
    }

    public Health getHealth() {
        return Health.builder()
                .date(new Timestamp(System.currentTimeMillis()))
                .total_request((long) count_total_request.count())
                .total_count_api_calls((long) count_total_count_api_calls.count())
                .avg_response_time(totalAvgResponseTime())
                .avg_response_time_api_calls(totalAvgResponseTimeApiCalls())
                .build();
    }

    private Long totalAvgResponseTime() {
        Long time = 0L;
        for (Long ttime : avg_response_time) {
            time += ttime;
        }

        return (time / avg_response_time.size());
    }

    private Long totalAvgResponseTimeApiCalls() {
        Long time = 0L;
        for (Long ttime : avg_response_time_api_calls) {
            time += ttime;
        }

        return (time / avg_response_time_api_calls.size());
    }

}
