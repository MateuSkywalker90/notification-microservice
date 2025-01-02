package com.mateus.notification.listener;

import com.mateus.notification.constant.ConstantMessage;
import com.mateus.notification.domain.Proposals;
import com.mateus.notification.service.SnsNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PendingProposalListener {

    @Autowired
    private SnsNotificationService snsNotificationService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void pendingProposal(Proposals proposals) {
        String message = String.format(ConstantMessage.PROPOSAL_UNDER_ANALYSIS, proposals.getUsuario().getNome());
        snsNotificationService.notify(proposals.getUsuario().getTelefone(), message);
    }
}
