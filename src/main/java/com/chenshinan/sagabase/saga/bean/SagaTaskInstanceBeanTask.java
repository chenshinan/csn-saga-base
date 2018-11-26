package com.chenshinan.sagabase.saga.bean;

import com.chenshinan.sagabase.saga.SagaMonitor;
import com.chenshinan.sagabase.saga.feign.SagaMonitorClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author shinan.chen
 * @date 2018/9/3
 */
public class SagaTaskInstanceBeanTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SagaTaskInstanceBeanTask.class);

    private SagaTaskInstanceBean instanceBean;

    private SagaTaskInvokeBean invokeBean;

    private DataSourceTransactionManager dataSourceTransactionManager;

    private SagaMonitorClient sagaMonitorClient;

    public SagaTaskInstanceBeanTask(SagaTaskInstanceBean instanceBean, DataSourceTransactionManager dataSourceTransactionManager, SagaMonitorClient sagaMonitorClient) {
        this.instanceBean = instanceBean;
        this.dataSourceTransactionManager = dataSourceTransactionManager;
        this.invokeBean = SagaMonitor.invokeBeanMap.get(instanceBean.getSagaCode() + instanceBean.getSagaTaskCode());
        this.sagaMonitorClient = sagaMonitorClient;
    }

    @Override
    public void run() {
        try {
            invoke(instanceBean);
        } catch (Exception e) {
            LOGGER.error("sagaMonitor consume message error, cause {}", e);
        } finally {
            SagaMonitor.msgQueue.remove(instanceBean);
        }

    }

    private void invoke(SagaTaskInstanceBean instanceBean) {
        //编程式事务，定义事务属性
        DefaultTransactionDefinition transDef = new DefaultTransactionDefinition();
        //设置传播行为属性
        transDef.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        //获得事务状态
        TransactionStatus status = dataSourceTransactionManager.getTransaction(transDef);
        try {
            //得作用就是让我们在用反射时访问私有变量
            invokeBean.method.setAccessible(true);
            //反射调用
            Object result = invokeBean.method.invoke(invokeBean.object, instanceBean.getInput());
            sagaMonitorClient.updateStatus("test");
            LOGGER.info("invoke result:{}", result);
            //提交事务
            dataSourceTransactionManager.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            dataSourceTransactionManager.rollback(status);
        }
    }
}
