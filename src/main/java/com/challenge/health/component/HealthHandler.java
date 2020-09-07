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

/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
@Component
public class HealthHandler {

    private final MeterRegistry meterRegistry;
    private Counter count_total_count_api_calls;
    private Counter count_total_request;

    private List<Long> avg_response_time;
    private List<Long> avg_response_time_api_calls;
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
        mapInfRequest = new HashMap<>();
    }

    public void register(Long time, Integer code) {
        Long total = (System.currentTimeMillis() - time) / 1000;
        avg_response_time.add(total);

        count_total_request.increment(1.0);
        registerWithCode(code);
    }

    public void registerWithCode(Integer errorCode) {
        if (!mapInfRequest.containsKey(errorCode)) {
            mapInfRequest.put(errorCode, Counter.builder("with_code_" + errorCode)
                    .tag("with_code_" + errorCode, "0")
                    .register(meterRegistry));
        }
        mapInfRequest.get(errorCode).increment(1.0);
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
                .avg_response_time(buildTotalAvgResponseTime())
                .avg_response_time_api_calls(buildTotalAvgResponseTimeApiCalls())
                .info_requests(buildInfoRequest())
                .build();
    }

    private Long buildTotalAvgResponseTime() {
        if (avg_response_time.isEmpty()) {
            return 0L;
        }
        Long time = 0L;
        time = avg_response_time.stream().map((ttime) -> ttime).reduce(time, (accumulator, _item) -> accumulator + _item);

        return (time / avg_response_time.size());
    }

    private Long buildTotalAvgResponseTimeApiCalls() {
        if (avg_response_time_api_calls.isEmpty()) {
            return 0L;
        }
        Long time = 0L;
        time = avg_response_time_api_calls.stream().map((ttime) -> ttime).reduce(time, (accumulator, _item) -> accumulator + _item);

        return (time / avg_response_time_api_calls.size());
    }

    private List<InfoRequest> buildInfoRequest() {
        List<InfoRequest> requests = new ArrayList<>();
        mapInfRequest.entrySet().stream().map((entry) -> {
            Integer key = entry.getKey();
            Counter value = entry.getValue();
            InfoRequest infoRequest = InfoRequest.builder()
                    .status_code(key)
                    .count((int) value.count())
                    .build();
            return infoRequest;
        }).forEachOrdered((infoRequest) -> {
            requests.add(infoRequest);
        });
        return requests;
    }

}
