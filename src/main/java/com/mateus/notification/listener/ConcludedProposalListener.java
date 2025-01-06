package com.mateus.notification.listener;

import com.mateus.notification.constant.ConstantMessage;
import com.mateus.notification.domain.Proposals;
import com.mateus.notification.service.SnsNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class ConcludedProposalListener {

    @Autowired
    private SnsNotificationService snsNotificationService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void pendingProposal(Proposals proposals) {
        String nome = proposals.getUsuario().getNome();

        String message = proposals.getAprovada()
                ? String.format(ConstantMessage.APPROVED_PROPOSAL, nome)
                : (Objects.nonNull(proposals.getObservacao())
                ? String.format(ConstantMessage.DENIED_PROPOSAL_BY_STRATEGY, nome, proposals.getObservacao())
                : String.format(ConstantMessage.DENIED_PROPOSAL, nome));

        snsNotificationService.notify(proposals.getUsuario().getTelefone(), message);
    }
}
