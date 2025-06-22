package com.bank.bankwithdrawalapi.infrastructure;

import com.bank.bankwithdrawalapi.domain.IEventPublisher;
import com.bank.bankwithdrawalapi.domain.WithdrawalEvent;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
class EventPublisherImp implements IEventPublisher {

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
        try {
            _snsClient.publish(publishRequest);
        }
        catch (Exception ex)
        {
            // assuming a business here is that event publishing is fire & forget so publishing failures should not hinder a "successful withdrawal request".
            // there swallowing the exception here but need to log it with a logger so this can be tracked via observability/monitoring.
        }
    }

}
