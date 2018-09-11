package com.chenshinan.sagabase.saga.bean;

import com.chenshinan.sagabase.saga.SagaMonitor;
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

    private SagaTaskInstanceBean sagaTaskInstanceBean;

    private DataSourceTransactionManager dataSourceTransactionManager;

    public SagaTaskInstanceBeanTask(SagaTaskInstanceBean sagaTaskInstanceBean, DataSourceTransactionManager dataSourceTransactionManager) {
        this.sagaTaskInstanceBean = sagaTaskInstanceBean;
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Override
    public void run() {
        LOGGER.info("sagaTaskInstanceBean:{}", sagaTaskInstanceBean);
        SagaTaskInvokeBean invokeBean = SagaMonitor.invokeBeanMap.get(sagaTaskInstanceBean.getCode());

        //编程式事务
        DefaultTransactionDefinition transDef = new DefaultTransactionDefinition(); // 定义事务属性
        transDef.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED); // 设置传播行为属性
        TransactionStatus status = dataSourceTransactionManager.getTransaction(transDef); // 获得事务状态
        try {
            Object result = invokeBean.method.invoke(invokeBean.object, sagaTaskInstanceBean.getInput());
            LOGGER.info("invoke result:{}", result);
            // 数据库操作
            dataSourceTransactionManager.commit(status);// 提交
        } catch (Exception e) {
            e.printStackTrace();
            dataSourceTransactionManager.rollback(status);// 回滚
        }

    }
}
