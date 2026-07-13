package com.project.movie.dto.response;

import java.math.BigDecimal;

import com.project.movie.model.EnumTicketStatus;

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
public class TicketResponse {

	private Integer ticketId;
	
	private Integer userId;
	
	private Integer seatId;
	
	private Integer scheduleId;
	
	private BigDecimal price;
	
	private EnumTicketStatus ticketStatus;
}
