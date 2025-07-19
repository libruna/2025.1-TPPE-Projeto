package com.smartmanage.api.service.impl;

import com.smartmanage.api.dto.request.ItemRequestDto;
import com.smartmanage.api.dto.request.OrderRequestDto;
import com.smartmanage.api.dto.response.OrderResponseDto;
import com.smartmanage.api.enums.OrderStatusEnum;
import com.smartmanage.api.model.entity.*;
import com.smartmanage.api.repository.*;
import com.smartmanage.api.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository, PaymentMethodRepository paymentMethodRepository,
                            OrderStatusRepository orderStatusRepository, ClientRepository clientRepository,
                            ProductRepository productRepository, EmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.employeeRepository = employeeRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public OrderResponseDto saveOrder(UUID clientId, OrderRequestDto orderRequestDto, String employeeId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

        Employee employee = employeeRepository.findById(UUID.fromString(employeeId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado."));

        PaymentMethod paymentMethod = paymentMethodRepository.getReferenceById(orderRequestDto.getPaymentMethod().getId());

        OrderStatus orderStatus = orderStatusRepository.findByName(OrderStatusEnum.COMPLETED.name()).orElse(null);

        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);

        Order order = new Order();
        order.setClient(client);
        order.setEmployee(employee);
        order.setPayment(payment);
        order.setOrderStatus(orderStatus);
        order.setItems(new HashSet<>());

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ItemRequestDto itemRequestDto : orderRequestDto.getItems()) {
            OrderItem orderItem = buildOrderItem(itemRequestDto, order);
            order.getItems().add(orderItem);

            totalPrice = totalPrice.add(orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(itemRequestDto.getNumber())));
        }

        if (orderRequestDto.getDiscountPercentage() != null && orderRequestDto.getDiscountPercentage() > 0) {
            BigDecimal percentage = BigDecimal.valueOf(orderRequestDto.getDiscountPercentage())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            order.setTotalPrice(totalPrice.subtract(totalPrice.multiply(percentage)));
        } else {
            order.setTotalPrice(totalPrice);
        }

        return OrderResponseDto.builder()
                .id(orderRepository.save(order).getId())
                .build();
    }

    @Override
    public List<OrderResponseDto> findAllByClientId(UUID clientId) {
        return mapper.map(orderRepository.findAllByClientId(clientId), new TypeToken<List<OrderResponseDto>>() {}.getType());
    }

    private OrderItem buildOrderItem(ItemRequestDto itemRequestDto, Order order) {
        Product product = productRepository.findById(itemRequestDto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        orderItem.setNumber(itemRequestDto.getNumber());
        return orderItem;
    }
}
