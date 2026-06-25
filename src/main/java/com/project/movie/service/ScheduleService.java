package com.project.movie.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.movie.dto.request.ScheduleRequest;
import com.project.movie.dto.response.ScheduleResponse;
import com.project.movie.exception.NotFoundException;
import com.project.movie.model.Movie;
import com.project.movie.model.Schedule;
import com.project.movie.repository.MovieRepository;
import com.project.movie.repository.ScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final MovieRepository movieRepository;

	// get schedules
	public List<ScheduleResponse> getSchedules() {
		return scheduleRepository.findAll().stream().map(this::toResponse).toList();
	}

	// get schedule by id
	public ScheduleResponse getScheduleById(Integer id) {
		Schedule schedule = scheduleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("ไม่พบ Schedule ID: " + id));

		return toResponse(schedule);
	}

	// create schedule
	@Transactional
	public ScheduleResponse createSchedule(ScheduleRequest request) {
		Movie movie = movieRepository.findById(request.getMovieId())
				.orElseThrow(() -> new NotFoundException("ไม่พบ Movie ID: " + request.getMovieId()));

		Schedule schedule = Schedule.builder().startDate(request.getStartDate()).endDate(request.getEndDate())
				.showTime(request.getShowTime()).availableSeats(request.getAvailableSeats()).movie(movie).build();

		return toResponse(scheduleRepository.save(schedule));
	}

	// update schedule
	@Transactional
	public ScheduleResponse updateSchedule(Integer id, ScheduleRequest request) {
		Schedule schedule = scheduleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("ไม่พบ Schedule ID: " + id));

		Movie movie = movieRepository.findById(request.getMovieId())
				.orElseThrow(() -> new NotFoundException("ไม่พบ Movie ID: " + request.getMovieId()));

		schedule.setStartDate(request.getStartDate());
		schedule.setEndDate(request.getEndDate());
		schedule.setShowTime(request.getShowTime());
		schedule.setAvailableSeats(request.getAvailableSeats());
		schedule.setMovie(movie);

		return toResponse(scheduleRepository.save(schedule));

	}

	// delete schedule
	public void deleteSchedule(Integer id) {
		if (!scheduleRepository.existsById(id)) {
			throw new NotFoundException("ไม่พบ Schedule ID: " + id);
		}
		scheduleRepository.deleteById(id);
	}

	public ScheduleResponse toResponse(Schedule schedule) {
		return ScheduleResponse.builder().scheduleId(schedule.getScheduleId()).startDate(schedule.getStartDate())
				.endDate(schedule.getEndDate()).showTime(schedule.getShowTime())
				.availableSeats(schedule.getAvailableSeats()).movieId(schedule.getMovie().getMovieId())
				.movieName(schedule.getMovie().getMovieName()).build();
	}

}
