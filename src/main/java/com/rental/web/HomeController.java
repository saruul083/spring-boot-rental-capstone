package com.rental.web;

import com.rental.model.Booking;
import com.rental.model.Item;
import com.rental.model.Customer;
import com.rental.service.BookingService;
import com.rental.service.ItemService;
import com.rental.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String home(Model model) {
        long itemCount = itemService.findAll().size();
        long bookingCount = bookingService.findAll().size();
        model.addAttribute("itemCount", itemCount);
        model.addAttribute("bookingCount", bookingCount);
        return "index";
    }

    @GetMapping("/items")
    public String listItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items/list";
    }

    @GetMapping("/items/new")
    public String newItemForm(Model model) {
        model.addAttribute("categories", itemService.findAll().isEmpty() ? List.of() : itemService.findAll().stream().map(Item::getCategory).distinct().toList());
        model.addAttribute("item", new Item());
        return "items/form";
    }

    @PostMapping("/items/save")
    public String saveItem(@ModelAttribute Item item) {
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping("/items/edit/{id}")
    public String editItemForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id)));
        return "items/form";
    }

    @GetMapping("/customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customers/list";
    }

    @GetMapping("/customers/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/form";
    }

    @PostMapping("/customers/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/bookings/new")
    public String newBookingForm(Model model) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("booking", new Booking());
        return "bookings/form";
    }

    @PostMapping("/bookings/save")
    public String saveBooking(@ModelAttribute Booking booking,
                              @RequestParam("itemId") Long itemId,
                              @RequestParam("customerId") Long customerId,
                              @RequestParam("startDate") String startDateStr,
                              @RequestParam("endDate") String endDateStr,
                              Model model) {
        Item item = itemService.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + itemId));
        Customer customer = customerService.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + customerId));

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        booking = bookingService.createBooking(item, customer, startDate, endDate);
        model.addAttribute("booking", booking);
        model.addAttribute("successMessage", "Booking confirmed successfully!");
        return "bookings/confirmation";
    }

    @GetMapping("/bookings")
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        return "bookings/list";
    }
}