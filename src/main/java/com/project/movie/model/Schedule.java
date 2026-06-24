package com.project.movie.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.ToString;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
	
	@Id
	@EqualsAndHashCode.Include
	@SequenceGenerator(name = "schedule_id_seq", sequenceName = "schedule_id_seq", allocationSize =1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_id_seq")
	@Column(nullable = false)
	private Integer scheduleId;
	
	@NotNull
	@Column(nullable = false)
	private LocalDate startDate;
	
	@NotNull
	@Column(nullable = false)
	private LocalDate endDate;
	
	@NotNull
	@Column(nullable = false)
	private LocalTime showTime;
	
	@Column(name = "available_seats")
	private Integer availableSeats;
	
	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;
	
}
