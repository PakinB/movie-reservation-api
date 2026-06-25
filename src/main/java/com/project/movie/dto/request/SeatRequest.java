package com.project.movie.dto.request;

import com.project.movie.model.EnumSeatStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequest {
	
	@NotNull
	private Integer seatNumber;
	
	@NotBlank
	private String seatRow;
	
	@NotNull
	private EnumSeatStatus seatStatus;
	
	@NotNull
	private Integer scheduleId;
}
