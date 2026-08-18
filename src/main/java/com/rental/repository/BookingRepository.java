package com.rental.repository;

import com.rental.model.Booking;
import com.rental.model.Item;
import com.rental.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByItemIdAndStatus(Long itemId, BookingStatus status);

    List<Booking> findOverlappingBookings(Item item, LocalDate startDate, LocalDate endDate);

    boolean existsByItemIdAndStatusAndStartDateLessThanAndEndDateGreaterThan(
            Long itemId, BookingStatus status,
            LocalDate startDate, LocalDate endDate);
}