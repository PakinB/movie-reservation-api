package com.project.movie.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
	
	@Id
	@EqualsAndHashCode.Include
	@SequenceGenerator(name = "ticket_id_seq", sequenceName = "ticket_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_seq")
	@Column(nullable = false)
	private Integer ticketId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "seat_id", nullable = false)
	private Seat seat;
	
	@ManyToOne
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal price;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EnumTicketStatus ticketStatus;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	

}
