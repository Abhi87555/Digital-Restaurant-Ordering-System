package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.model.Order;
import com.jack.sparrow.potc.restaurantmanagement.model.Payment;
import com.jack.sparrow.potc.restaurantmanagement.repository.OrderRepository;
import com.jack.sparrow.potc.restaurantmanagement.repository.PaymentRepository;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.OrderRestModel;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.PaymentRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class PaymentService {

    private static final String[] validPaymentMethod = {"CASH", "CREDIT_CARD", "DEBIT_CARD", "UPI"};

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Transactional
    public PaymentRestModel recordPayment(PaymentRestModel paymentObj) {
        validatePayment(paymentObj);
        try {
            Order order = orderRepository.findById(paymentObj.getOrderId()).get();
            Payment payment = new Payment(order, paymentObj.getPaymentMethod(), paymentObj.getPaymentStatus(),
                    paymentObj.getAmountPaid(), new Timestamp(new Date().getTime()));

            if ("PAID".equals(paymentObj.getPaymentStatus())){
                OrderRestModel orderObj = new OrderRestModel(order.getOrderId(), "COMPLETED");
                orderService.updateOrder(orderObj);
            }
            paymentRepository.save(payment);
            return paymentObj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public PaymentRestModel updatePayment(PaymentRestModel paymentObj) {
        validateUpdatePayment(paymentObj);
        try {
            Payment payment = paymentRepository.findById(paymentObj.getPaymentId()).get();
            Order order = payment.getOrder();
            payment.setPaymentStatus("PAID");

            if ("PAID".equals(paymentObj.getPaymentStatus())){
                OrderRestModel orderObj = new OrderRestModel(order.getOrderId(), "COMPLETED");
                orderService.updateOrder(orderObj);
            }

            paymentRepository.save(payment);
            return paymentObj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateUpdatePayment(PaymentRestModel paymentObj) {
        if (paymentObj.getPaymentId() == null) {
            throw new RuntimeException("Payment id cannot be null");
        }
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentObj.getPaymentId());
        if (!paymentOptional.isPresent()){
            throw new RuntimeException("Invalid payment id");
        }
        if ("PAID".equals(paymentOptional.get().getPaymentStatus())) {
            throw new RuntimeException("Payment already recorded for this order");
        }
    }

    private void validatePayment(PaymentRestModel paymentObj) {
        if (paymentObj.getOrderId() == null) {
            throw new RuntimeException("Order id cannot be null");
        }
        Optional<Order> orderOptional = orderRepository.findById(paymentObj.getOrderId());
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("Invalid order id");
        }
        if (paymentObj.getPaymentMethod() == null) {
            throw new RuntimeException("Payment method cannot be null");
        }
        if (!Arrays.asList(validPaymentMethod).contains(paymentObj.getPaymentMethod())) {
            throw new RuntimeException("Invalid payment method");
        }
        if (paymentObj.getPaymentStatus() != null) {
            if (!"UNPAID".equals(paymentObj.getPaymentStatus())
                    && "PAID".equals(paymentObj.getPaymentStatus())) {
                throw new RuntimeException("Invalid payment status");
            }
        }
        if (!orderOptional.get().getTotalCost().equals(paymentObj.getAmountPaid())) {
            throw new RuntimeException("Amount paid is not equal to total cost of the order");
        }
    }
}
