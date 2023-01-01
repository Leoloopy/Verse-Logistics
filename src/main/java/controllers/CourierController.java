package controllers;

import data.dtos.request.DeliveryStatusRequest;
import data.dtos.request.NewUserRequest;
import data.dtos.response.DeliveryStatusResponse;
import data.dtos.response.NewUserResponse;
import data.models.DeliveryStatus;
import data.models.Order;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.UserService;

import java.util.List;

@RestController
@RequestMapping (path = "users/courier/")
public class CourierController {
    @Autowired
    @Qualifier("courier")
    private UserService userService;

    @PostMapping("register/")
    public ResponseEntity<NewUserResponse> registerCourier(@Valid @RequestBody NewUserRequest request){
        NewUserResponse registeredCourier = userService.registerUser(request);
        return new ResponseEntity<>(registeredCourier, HttpStatus.CREATED);
    }

    @GetMapping("/check-delivery-status/{orderId}")
    public ResponseEntity<DeliveryStatus> checkOrderStatus(@PathVariable String orderId){
        DeliveryStatus orderStatus = userService.checkDeliveryStatus(orderId);
        return ResponseEntity.ok(orderStatus);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<Order>> getAllOrders(){
    List<Order> allOrders = userService.getAllOrders();
    return ResponseEntity.ok(allOrders);
    }

    @PostMapping("/confirm-delivery-status")
    public ResponseEntity<DeliveryStatusResponse> confirmDeliveryStatus(@RequestBody DeliveryStatusRequest req){
        var  status = userService.confirmDeliveryStatus(req);
        return new ResponseEntity<>(status,HttpStatus.ACCEPTED);
    }




}
