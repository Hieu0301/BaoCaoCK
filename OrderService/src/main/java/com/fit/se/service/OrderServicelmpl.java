package com.fit.se.service;

import com.fit.se.entity.Clothing;
import com.fit.se.entity.Order;
import com.fit.se.repository.OrderRepository; // Chỉnh sửa tên repository
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServicelmpl implements OrderService { // Đổi tên thành EmployeeServiceImpl

    @Autowired
    private OrderRepository orderRepository; // Chỉnh sửa tên repository
    private RestTemplate restTemplate;

    @Override
    public Order saveOrder(Order order) {
        ResponseEntity<Clothing> responseEntity = restTemplate
                .getForEntity("http://localhost:8083/clothings/" + order.getClothing().getId(),
                        Clothing.class);
        Clothing clothing = responseEntity.getBody();
        order.setClothing(clothing);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(int id) { // Chỉnh sửa tên phương thức
        return orderRepository.findById(id).orElse(null); // Sử dụng orElse để tránh trả về null nếu không tìm thấy
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }


    @Override
    public void deleteOrderById(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order updateOrderById(int id, Order newOrder) {
        Order tempOrder = orderRepository.findById(id).orElse(null); // Sử dụng orElse để tránh trả về null nếu không tìm thấy
        if (tempOrder != null) { // Kiểm tra xem nhân viên có tồn tại không trước khi cập nhật
            tempOrder.setDate(newOrder.getDate());
            tempOrder.setTime(newOrder.getTime());
            tempOrder.setPaymentMethods(newOrder.getPaymentMethods());
            ResponseEntity<Clothing> responseEntity = restTemplate
                    .getForEntity("http://localhost:8083/clothings/" + newOrder.getClothing().getId(),
                            Clothing.class);
            Clothing clothing = responseEntity.getBody();
            tempOrder.setClothing(clothing);
            return orderRepository.save(tempOrder);
        }
        return null;
    }

}
