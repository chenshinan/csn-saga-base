package com.chenshinan.sagabase.saga;

import com.chenshinan.sagabase.saga.bean.SagaTaskInstanceBean;
import com.chenshinan.sagabase.saga.bean.SagaTaskInstanceBeanTask;
import com.chenshinan.sagabase.saga.bean.SagaTaskInvokeBean;
import com.chenshinan.sagabase.saga.feign.SagaMonitorClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
@Component
public class SagaMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SagaMonitor.class);

    public static final Map<String, SagaTaskInvokeBean> invokeBeanMap = new HashMap<>();

    public static Set<SagaTaskInstanceBean> msgQueue;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private Executor executor;
    @Autowired
    Environment environment;
    @Autowired
    private SagaMonitorClient sagaMonitorClient;

    public SagaMonitor() {
        msgQueue = Collections.synchronizedSet(new LinkedHashSet<>());
    }

    /**
     * 注入线程池
     *
     * @return
     */
    @Bean
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(99999);
        executor.setThreadNamePrefix("csn-consumer-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @PostConstruct
    private void start() {
        LOGGER.info("invokeBeanMap现在有多少个呢：{}", invokeBeanMap.size());
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        try {
            String instance = InetAddress.getLocalHost().getHostAddress() + ":" + environment.getProperty("server.port");
            scheduledExecutorService.scheduleWithFixedDelay(() -> {
                invokeRunner();
            }, 1, 5000, TimeUnit.MILLISECONDS);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        /**
         * 测试区域【TEST】
         */
        //时间工具包，可获取类型枚举、计算、延迟、转换时间等
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(30000));
        //类工具包，可获取类名、包名、方法、与类有关的所有信息等
        System.out.println(ClassUtils.getShortName(SagaMonitor.class));
    }

    private void invokeRunner() {
        if (!msgQueue.isEmpty()) {
            //模拟从sagaManager获取到的数据
//            SagaTaskInstanceBean demo = new SagaTaskInstanceBean();
//            demo.setSagaTaskCode("producerCode");
//            demo.setInput("1");
//            demo.setOutput("2");
            List<SagaTaskInstanceBean> instanceBeans = sagaMonitorClient.pollTask("test");
            msgQueue.addAll(instanceBeans);
            msgQueue.forEach(instance -> {
                executor.execute(new SagaTaskInstanceBeanTask(instance, dataSourceTransactionManager, sagaMonitorClient));
            });
        }
    }
}
