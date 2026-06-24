package com.project.movie.model;

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
@Table(name = "seats")
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

	@Id
	@EqualsAndHashCode.Include
	@SequenceGenerator(name = "seat_id_seq", sequenceName = "seat_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_id_seq")
	@Column(nullable = false)
	private Integer seatId;
	
	@NotNull
	@Column(nullable = false)
	private Integer seatNumber;
	
	@NotNull
	@Column(nullable = false)
	private String seatRow;

	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumSeatStatus seatStatus;
	
	@ManyToOne
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;
}
