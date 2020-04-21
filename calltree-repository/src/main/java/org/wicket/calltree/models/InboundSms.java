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
@Table(name = "inbound_sms")
public class InboundSms {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "to_country")
    private String toCountry;

    @Column(name = "sms_status")
    @NotNull
    private String smsStatus;

    @Column(name = "body")
    @NotNull
    private String body;

    @Column(name = "from_country")
    private String fromCountry;

    @Column(name = "from_contact_number")
    @NotNull
    private String fromContactNumber;

    @Column(name = "timestamp")
    @NotNull
    private String timestamp;

    @Version
    private Long version;
}
