package com.dach.reservation_tool;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
abstract class Reservation<ID> {
        @Id
        @Column(name = "id", nullable = false, length = 255)
        @GeneratedValue(strategy = GenerationType.UUID)
        private ID id;
}
