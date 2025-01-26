package com.kubassile.kubassile.domain.client;

import java.time.LocalDateTime;

import org.hibernate.annotations.CurrentTimestamp;

import com.kubassile.kubassile.domain.client.dto.ClientDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String clientName;

    private String phone;

    @Column(name = "created_at")
    @CurrentTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @CurrentTimestamp
    private LocalDateTime updatedAt;

    public Client(ClientDto data) {
        this.clientName = data.clientName();
        this.phone = data.phone();
    }
}
