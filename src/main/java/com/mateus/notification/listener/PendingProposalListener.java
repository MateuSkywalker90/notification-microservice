package com.mateus.notification.listener;

import com.mateus.notification.constant.ConstantMessage;
import com.mateus.notification.domain.Proposal;
import com.mateus.notification.service.SnsNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PendingProposalListener {

    private final SnsNotificationService snsNotificationService;

    public PendingProposalListener(SnsNotificationService snsNotificationService) {
        this.snsNotificationService = snsNotificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.pending.proposal}")
    public void pendingProposal(Proposal proposal) {
        String message = String.format(ConstantMessage.PROPOSAL_UNDER_ANALYSIS, proposal.getUser().getFirstName());
        snsNotificationService.notify(proposal.getUser().getPhone(), message);
    }
}
