package com.rental.service;

import com.rental.model.Booking;
import com.rental.model.Item;
import com.rental.model.Customer;
import com.rental.repository.BookingRepository;
import com.rental.repository.ItemRepository;
import com.rental.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Booking createBooking(Item item, Customer customer, LocalDate startDate, LocalDate endDate) {
        // Conflict check: reject if overlapping confirmed booking exists
        if (isOverlappingBooking(item, startDate, endDate)) {
            throw new IllegalStateException("This item is already booked for the selected dates. Please choose different dates.");
        }

        Booking booking = new Booking(item, customer, startDate, endDate);
        booking = bookingRepository.save(booking);
        item.setAvailable(false);
        itemRepository.save(item);
        return booking;
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + bookingId));
        booking.setStatus(BookingStatus.CANCELLED);
        booking.getItem().setAvailable(true);
        bookingRepository.save(booking);
    }

    public boolean isOverlappingBooking(Item item, LocalDate startDate, LocalDate endDate) {
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(item, startDate, endDate);
        return !overlapping.isEmpty();
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }