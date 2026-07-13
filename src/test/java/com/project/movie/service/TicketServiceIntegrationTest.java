package com.project.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.project.movie.dto.request.TicketRequest;
import com.project.movie.dto.response.TicketResponse;
import com.project.movie.exception.InvalidSeatStatusException;
import com.project.movie.model.EnumRole;
import com.project.movie.model.EnumSeatStatus;
import com.project.movie.model.EnumTicketStatus;
import com.project.movie.model.Movie;
import com.project.movie.model.Schedule;
import com.project.movie.model.Seat;
import com.project.movie.model.Ticket;
import com.project.movie.model.User;
import com.project.movie.repository.MovieRepository;
import com.project.movie.repository.ScheduleRepository;
import com.project.movie.repository.SeatRepository;
import com.project.movie.repository.TicketRepository;
import com.project.movie.repository.UserRepository;

@SpringBootTest
@Testcontainers
class TicketServiceIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void configureRedis(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired private TicketService ticketService;
    @Autowired private UserRepository userRepository;
    @Autowired private MovieRepository movieRepository;
    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private SeatRepository seatRepository;
    @Autowired private TicketRepository ticketRepository;

    private User testUser;
    private Seat testSeat;
    private Schedule testSchedule;

    @BeforeEach
    void setUp() {
        testUser = userRepository.save(User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("hashed_password")
                .role(EnumRole.USER)
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .movieName("Test Movie")
                .movieYear(2025)
                .genre("Action")
                .description("Test description")
                .build());

        testSchedule = scheduleRepository.save(Schedule.builder()
                .movie(movie)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(7))
                .showTime(LocalTime.of(18, 0))
                .availableSeats(100)
                .build());

        testSeat = seatRepository.save(Seat.builder()
                .seatNumber(1)
                .seatRow("A")
                .seatStatus(EnumSeatStatus.AVAILABLE)
                .schedule(testSchedule)
                .build());
    }

    @AfterEach
    void tearDown() {
        ticketRepository.deleteAll();
        seatRepository.deleteAll();
        scheduleRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createTicket_shouldSucceed_whenSeatIsAvailable() {
        TicketRequest request = TicketRequest.builder()
                .seatId(testSeat.getSeatId())
                .scheduleId(testSchedule.getScheduleId())
                .price(new BigDecimal("250.00"))
                .build();

        TicketResponse response = ticketService.createTicket(request, testUser.getId());

        assertThat(response).isNotNull();
        assertThat(response.getTicketStatus()).isEqualTo(EnumTicketStatus.CONFIRMED);

        Seat updatedSeat = seatRepository.findById(testSeat.getSeatId()).orElseThrow();
        assertThat(updatedSeat.getSeatStatus()).isEqualTo(EnumSeatStatus.BOOKED);
    }

    @Test
    void createTicket_shouldThrow_whenSeatIsAlreadyBooked() {
        testSeat.setSeatStatus(EnumSeatStatus.BOOKED);
        seatRepository.save(testSeat);

        TicketRequest request = TicketRequest.builder()
                .seatId(testSeat.getSeatId())
                .scheduleId(testSchedule.getScheduleId())
                .price(new BigDecimal("250.00"))
                .build();

        assertThrows(InvalidSeatStatusException.class,
                () -> ticketService.createTicket(request, testUser.getId()));
    }

    @Test
    void holdSeat_shouldSucceed_andSetStatusToPendingAndReserved() {
        TicketRequest request = TicketRequest.builder()
                .seatId(testSeat.getSeatId())
                .scheduleId(testSchedule.getScheduleId())
                .price(new BigDecimal("250.00"))
                .build();

        TicketResponse response = ticketService.holdSeat(request, testUser.getId());

        assertThat(response.getTicketStatus()).isEqualTo(EnumTicketStatus.PENDING);

        Seat updatedSeat = seatRepository.findById(testSeat.getSeatId()).orElseThrow();
        assertThat(updatedSeat.getSeatStatus()).isEqualTo(EnumSeatStatus.RESERVED);
    }

    @Test
    void cancelTicket_shouldSucceed_andReleaseSeatToAvailable() {
        testSeat.setSeatStatus(EnumSeatStatus.BOOKED);
        seatRepository.save(testSeat);

        Ticket ticket = ticketRepository.save(Ticket.builder()
                .user(testUser)
                .seat(testSeat)
                .schedule(testSchedule)
                .price(new BigDecimal("250.00"))
                .ticketStatus(EnumTicketStatus.CONFIRMED)
                .build());

        ticketService.cancelTicket(ticket.getTicketId(), testUser.getId());

        Ticket cancelled = ticketRepository.findById(ticket.getTicketId()).orElseThrow();
        assertThat(cancelled.getTicketStatus()).isEqualTo(EnumTicketStatus.CANCELLED);

        Seat released = seatRepository.findById(testSeat.getSeatId()).orElseThrow();
        assertThat(released.getSeatStatus()).isEqualTo(EnumSeatStatus.AVAILABLE);
    }
}
