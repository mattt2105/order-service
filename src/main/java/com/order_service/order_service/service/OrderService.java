package com.order_service.order_service.service;

import com.order_service.order_service.dto.BarberDTO;
import com.order_service.order_service.dto.CustomerDTO;
import com.order_service.order_service.dto.CustomerOrderDTO;
import com.order_service.order_service.model.Order;
import com.order_service.order_service.repository.OrderRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired
    OrderRepository repository;

    @Value("${customer.rest.path}")
    private String customer_url;

    @Value("${barber.rest.path}")
    private String barber_url;


    @Autowired
    private  RestTemplate restTemplate;


    public ResponseEntity<Order> createOrder (String idCustomer, String idBarber){
        CustomerDTO dataCustomer = findCustomerById(idCustomer);
        BarberDTO dataBarber = findBarberById(idBarber);

        if (dataCustomer != null && dataBarber != null){

            Order newOrder = new Order();
            newOrder.setNameCustomer(dataCustomer.getName());
            newOrder.setNameBarber(dataBarber.getName());
            newOrder.setOrderStatus("PENDING");

            repository.save(newOrder);
            return new ResponseEntity(dataCustomer, HttpStatus.OK);
        }

        return new ResponseEntity(dataCustomer, HttpStatus.BAD_REQUEST);
    }

    public List<CustomerOrderDTO> getCustomerOrders(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = repository.findCustomerOrders(startDate, endDate);
        return results.stream()
                .map(result -> new CustomerOrderDTO((String) result[0], ((Number) result[1]).longValue()))
                .collect(Collectors.toList());
    }

    public CustomerDTO findCustomerById(String idCustomer){
        if (idCustomer != null && !idCustomer.isEmpty()){
            String url = customer_url+ "api/customers/customer/get/by/id/"+idCustomer;
            ResponseEntity<CustomerDTO> reponse = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    CustomerDTO.class,
                    idCustomer);
            return reponse.getBody() == null ? new CustomerDTO() : reponse.getBody();
        }
        return new CustomerDTO();
    }

    public BarberDTO findBarberById(String idBarber){
        if (idBarber != null && !idBarber.isEmpty()){
            ResponseEntity<BarberDTO> reponse = restTemplate.exchange(
                    barber_url+"api/barbers/barber/get/by/id/"+idBarber,
                    HttpMethod.GET,
                    null,
                    BarberDTO.class,
                    idBarber);
            return reponse.getBody() == null ? new BarberDTO() : reponse.getBody();
        }
        return new BarberDTO();
    }

}
