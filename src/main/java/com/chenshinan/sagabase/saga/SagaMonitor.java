package com.chenshinan.sagabase.saga;

import com.chenshinan.sagabase.saga.bean.SagaInvokeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
@Component
public class SagaMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SagaMonitor.class);

    public static final Map<String, SagaInvokeBean> invokeBeanMap = new HashMap<>();

    @PostConstruct
    private void start() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            LOGGER.info("invokeBeanMap现在有多少个呢：{}", invokeBeanMap.size());

        }, 1, 5000, TimeUnit.MILLISECONDS);

        /**
         * 测试区域【TEST】
         */
        //时间工具包，可获取类型枚举、计算、延迟、转换时间等
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(30000));
        //类工具包，可获取类名、包名、方法、与类有关的所有信息等
        System.out.println(ClassUtils.getShortName(SagaMonitor.class));
    }
}
