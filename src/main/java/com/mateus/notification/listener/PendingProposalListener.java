package com.mateus.notification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PendingProposalListener {

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void pendingProposal() {

    }
}
