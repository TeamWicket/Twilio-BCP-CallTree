package org.wicket.calltree.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wicket.calltree.enums.SmsStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Alessandro Arosio - 13/04/2020 22:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "outbound_sms")
public class BcpEventSms {

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
