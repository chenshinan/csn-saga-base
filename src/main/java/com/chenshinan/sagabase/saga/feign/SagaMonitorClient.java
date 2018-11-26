package com.chenshinan.sagabase.saga.feign;

import com.chenshinan.sagabase.saga.bean.SagaTaskInstanceBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author shinan.chen
 * @since 2018/11/26
 */
@FeignClient(name = "csn-saga-monitor", fallback = SagaMonitorClientCallBack.class)
@Component
public interface SagaMonitorClient {
    @PostMapping("")
    List<SagaTaskInstanceBean> pollTask(@RequestParam("name") String name);

    @PostMapping("")
    void updateStatus(@RequestParam("name") String name);
}
