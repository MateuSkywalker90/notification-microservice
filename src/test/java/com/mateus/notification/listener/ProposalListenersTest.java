package com.mateus.notification.listener;

import com.mateus.notification.constant.ConstantMessage;
import com.mateus.notification.domain.Proposal;
import com.mateus.notification.domain.User;
import com.mateus.notification.service.SnsNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProposalListenersTest {

    private static final String PHONE = "+5592999999999";

    @Mock
    private SnsNotificationService snsNotificationService;

    @Test
    void bothListenersAreRegisteredAsSpringBeans() {
        assertThat(PendingProposalListener.class.isAnnotationPresent(Component.class)).isTrue();
        assertThat(ConcludedProposalListener.class.isAnnotationPresent(Component.class)).isTrue();
    }

    @Test
    void pendingProposalSendsUnderAnalysisSms() {
        new PendingProposalListener(snsNotificationService).pendingProposal(proposal(null, null));

        verify(snsNotificationService).notify(PHONE, String.format(ConstantMessage.PROPOSAL_UNDER_ANALYSIS, "Maria"));
    }

    @Test
    void approvedProposalSendsApprovedSms() {
        new ConcludedProposalListener(snsNotificationService).concludedProposal(proposal(true, null));

        verify(snsNotificationService).notify(PHONE, String.format(ConstantMessage.APPROVED_PROPOSAL, "Maria"));
    }

    @Test
    void deniedProposalWithReasonIncludesTheReason() {
        String message = new ConcludedProposalListener(snsNotificationService)
                .buildMessage(proposal(false, "Customer Maria has a bad credit"));

        assertThat(message).isEqualTo(String.format(ConstantMessage.DENIED_PROPOSAL_BY_STRATEGY,
                "Maria", "Customer Maria has a bad credit"));
    }

    @Test
    void deniedProposalWithoutReasonSendsGenericDenial() {
        String message = new ConcludedProposalListener(snsNotificationService).buildMessage(proposal(false, null));

        assertThat(message).isEqualTo(String.format(ConstantMessage.DENIED_PROPOSAL, "Maria"));
    }

    @Test
    void missingApprovalFlagIsTreatedAsDeniedInsteadOfFailing() {
        String message = new ConcludedProposalListener(snsNotificationService).buildMessage(proposal(null, null));

        assertThat(message).isEqualTo(String.format(ConstantMessage.DENIED_PROPOSAL, "Maria"));
    }

    private Proposal proposal(Boolean approved, String observation) {
        User user = new User();
        user.setFirstName("Maria");
        user.setPhone(PHONE);

        Proposal proposal = new Proposal();
        proposal.setUser(user);
        proposal.setApproved(approved);
        proposal.setObservation(observation);
        return proposal;
    }
}
