package com.mateus.notification.listener;

import com.mateus.notification.constant.ConstantMessage;
import com.mateus.notification.domain.Proposal;
import com.mateus.notification.service.SnsNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ConcludedProposalListener {

    private final SnsNotificationService snsNotificationService;

    public ConcludedProposalListener(SnsNotificationService snsNotificationService) {
        this.snsNotificationService = snsNotificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.concluded.proposal}")
    public void concludedProposal(Proposal proposal) {
        snsNotificationService.notify(proposal.getUser().getPhone(), buildMessage(proposal));
    }

    String buildMessage(Proposal proposal) {
        String firstName = proposal.getUser().getFirstName();

        if (Boolean.TRUE.equals(proposal.getApproved())) {
            return String.format(ConstantMessage.APPROVED_PROPOSAL, firstName);
        }
        if (Objects.nonNull(proposal.getObservation())) {
            return String.format(ConstantMessage.DENIED_PROPOSAL_BY_STRATEGY, firstName, proposal.getObservation());
        }
        return String.format(ConstantMessage.DENIED_PROPOSAL, firstName);
    }
}
