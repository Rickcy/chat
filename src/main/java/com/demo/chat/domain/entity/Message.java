package com.demo.chat.domain.entity;

import com.demo.chat.shared.AbstractEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Table(name = "messages")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message extends AbstractEntity<Message, Long> {

    @Id
    @GeneratedValue
    protected Long id;

    @NotNull
    private String owner;

    @NotNull
    private String text;


    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();

}
