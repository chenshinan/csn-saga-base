package com.chenshinan.sagabase.saga;

import com.chenshinan.sagabase.saga.annotation.Saga;
import org.springframework.stereotype.Component;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
@Component
public class SagaProducer {

    @Saga(code = "producerCode", description = "producerDescription")
    public void producer() {
        System.out.println("producer");
    }
}
