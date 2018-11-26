package com.chenshinan.sagabase.saga.feign;

import com.chenshinan.sagabase.saga.bean.SagaTaskInstanceBean;

import java.util.List;

/**
 * @author shinan.chen
 * @since 2018/11/26
 */
public class SagaMonitorClientCallBack implements SagaMonitorClient {
    @Override
    public List<SagaTaskInstanceBean> pollTask(String name) {
        return null;
    }

    @Override
    public void updateStatus(String name) {

    }
}
