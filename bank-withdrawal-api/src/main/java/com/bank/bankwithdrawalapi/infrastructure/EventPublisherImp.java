package com.bank.bankwithdrawalapi.infrastructure;

import com.bank.bankwithdrawalapi.domain.IEventPublisher;
import com.bank.bankwithdrawalapi.domain.WithdrawalEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Component
public class EventPublisherImp implements IEventPublisher {

    private final SnsClient _snsClient;
    private final String _topic = "arn:aws:sns:YOUR_REGION:YOUR_ACCOUNT_ID:YOUR_TOPIC_NAME";

    public EventPublisherImp() {
        _snsClient = SnsClient.builder()
                .region(Region.AWS_GLOBAL) // ideally we should pass this through config, hardcoded values are not good.
                .build();;
    }

    @Override
    public void publishEvent(WithdrawalEvent event) {
        PublishRequest publishRequest = PublishRequest.builder()
                .message(event.toJson())
                .topicArn(_topic)
                .build();
        PublishResponse publishResponse = _snsClient.publish(publishRequest);
    }

}
