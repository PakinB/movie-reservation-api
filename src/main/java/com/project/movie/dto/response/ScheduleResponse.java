package com.project.movie.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

	private Integer scheduleId;

	private LocalDate startDate;

	private LocalDate endDate;

	private LocalTime showTime;

	private Integer availableSeats;

	private Integer movieId;

	private String movieName;
}
