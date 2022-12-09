package com.bpr.main.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "BPR_C2B_NOTIFICATIONS")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CrbNotification {
    public CrbNotification(Blob request, Blob response, Date createdAt, String notificationType, String statusCode, String statusMessage, String accountNumber, String accountOwner, Integer retries) {
        this.request = request;
        this.response = response;
        this.createdAt = createdAt;
        this.notificationType = notificationType;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.accountNumber = accountNumber;
        this.accountOwner = accountOwner;
        this.retries = retries;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private Blob request;
    @Lob
    private Blob response;

    @Column(name = "CREATEDAT")
    private Date createdAt;
    @Column(name = "NOTIFICATIONTYPE")
    private String notificationType;
    @Column(name = "STATUSCODE")
    private String statusCode;
    @Column(name = "STATUSMESSAGE")
    private String statusMessage;
    @Column(name = "ACCOUNTNUMBER")
    private String accountNumber;
    @Column(name = "ACCOUNTOWNER")
    private String accountOwner;

    @Column(name = "RETRIES")
    private Integer retries;


}

