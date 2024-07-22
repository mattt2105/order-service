package com.order_service.order_service.controller;

import com.order_service.order_service.dto.CustomerOrderDTO;
import com.order_service.order_service.model.Order;
import com.order_service.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService service;

    @PostMapping("/order/create/{idCustomer}/{idBarber}")
    public ResponseEntity<Order> createCustomer(
            @PathVariable String idCustomer,
            @PathVariable String idBarber) {
        return service.createOrder(idCustomer, idBarber);
    }

//    @GetMapping("/customer-orders")
//    public List<CustomerOrderDTO> getCustomerOrders(
//            @RequestParam("startDate") String startDateStr,
//            @RequestParam("endDate") String endDateStr) {
//
//        LocalDate startDate = LocalDate.parse(startDateStr);
//        LocalDate endDate = LocalDate.parse(endDateStr);
//
//        LocalDateTime startDateTime = startDate.atStartOfDay();
//        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
//
//        return service.getCustomerOrders(startDateTime, endDateTime);
//    }

    @GetMapping("/customer-orders")
    public List<CustomerOrderDTO> getCustomerOrders(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        return service.getCustomerOrders(startDateTime, endDateTime);
    }
}
