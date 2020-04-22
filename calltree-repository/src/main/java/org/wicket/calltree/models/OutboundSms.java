package org.wicket.calltree.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class OutboundSms {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "date_created")
    private String dateCreated;

    @Column(name = "date_updated")
    private String dateUpdated;

    @Column(name = "date_sent")
    private String dateSent;

    @Column(name = "body")
    @NotNull
    private String body;

    @Column(name = "from_number")
    private String fromNumber;

    @NotNull
    @Column(name = "to_number")
    private String toNumber;

    @NotNull
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "bcp_event_id")
    @NotNull
    private BcpEvent bcpEvent;

    @Version
    private Long version;
}
