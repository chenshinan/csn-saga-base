package com.chenshinan.sagabase.saga;

import com.chenshinan.sagabase.saga.annotation.Saga;
import org.springframework.stereotype.Component;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
@Component
public class SagaConsumer {

    @Saga(code = "consumerCode", description = "consumerDescription")
    public void consumer() {
        System.out.println("consumer");
    }
}
