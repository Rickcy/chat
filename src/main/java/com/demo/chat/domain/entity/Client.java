package com.demo.chat.domain.entity;

import com.demo.chat.shared.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client extends AbstractEntity<Client, String> {

    @Id
    @Builder.Default
    protected String id = UUID.randomUUID().toString();

    @NonNull
    private String name;

    @NonNull
    private String password;

    @Builder.Default
    private boolean isEnabled = true;

    @Builder.Default
    private boolean isAccountNonExpired = true;

    @Builder.Default
    private boolean isAccountNonLocked = true;

    @Builder.Default
    private boolean isCredentialsNonExpired = true;


}
