package org.wicket.calltree.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wicket.calltree.enums.SmsStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bcp_message")
public class BcpMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bcp_event_id")
    @NotNull
    private BcpEvent bcpEvent;

    @NotNull
    @Column(name = "date_created")
    private String dateCreated;

    @Column(name = "date_updated")
    private String dateUpdated;

    @Column(name = "date_sent")
    private String dateSent;

    @NotNull
    @Column(name = "outbound_message")
    private String outboundMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "sms_status")
    private SmsStatus smsStatus;

    @Column(name = "recipient_number")
    @NotNull
    private String recipientNumber;

    @Column(name = "recipient_country")
    private String recipientCountry;

    @Column(name = "recipient_message")
    private String recipientMessage;

    @Column(name = "recipient_timestamp")
    private String recipientTimestamp;

    @Column(name = "error_message")
    private String errorMessage;

    @Version
    private Long version;
}
