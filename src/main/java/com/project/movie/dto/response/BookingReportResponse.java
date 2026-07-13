package com.project.movie.dto.response;


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
public class BookingReportResponse {

	
	private Integer scheduleId;
	
	private String movieTitle;
	
	private Integer totalBookings;
}
