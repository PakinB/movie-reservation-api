package com.project.movie.dto.response;

import com.project.movie.model.EnumSeatStatus;

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
public class SeatResponse {

	private Integer seatId;
	
	private Integer seatNumber;
	
	private String seatRow;

	private EnumSeatStatus seatStatus;
	
	private Integer scheduleId;
}
