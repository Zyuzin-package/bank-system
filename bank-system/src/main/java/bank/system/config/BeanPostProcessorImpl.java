package bank.system.config;

import bank.system.rest.Validator;
import bank.system.rest.exception.ServerException;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanPostProcessorImpl implements BeanPostProcessor {
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("validator")){
            Validator validator = (Validator) bean;
            if(validator.getMinCreditOfferDuration()>validator.getMaxCreditOfferDuration()){
                throw new ServerException("The minimum term of the credit offer must be less than the maximum");
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}

