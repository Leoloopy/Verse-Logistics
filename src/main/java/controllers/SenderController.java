package controllers;

import data.dtos.request.NewUserRequest;
import data.dtos.request.LoginRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.response.LoginResponse;
import data.dtos.response.NewOrderResponse;
import data.dtos.response.NewUserResponse;
import data.models.Order;
import data.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.UserService;

import java.util.List;


@RestController
@RequestMapping(path="/users/senders")
public class SenderController {

    @Autowired
    @Qualifier("sender")
    private UserService userService;

    @PostMapping(path="/register-user")
    public ResponseEntity<NewUserResponse> registerUser(@Valid @RequestBody NewUserRequest request){
        NewUserResponse res = userService.registerUser(request);
        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PostMapping(value="/login")
    public ResponseEntity<LoginResponse> logInToDashBoard(@Valid @RequestBody LoginRequest request){
        LoginResponse res = userService.LogIntoDashboard(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "/get-sender/{id}")
    public ResponseEntity<User> getSenderWithID(@PathVariable String id){
        User getSender = userService.getUserById(id);
        return ResponseEntity.ok(getSender);
    }

    @PostMapping("/send-order")
    public ResponseEntity<NewOrderResponse> createOrder(@RequestBody NewOrderRequest request){
        NewOrderResponse res = userService.sendOrder(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/cancel-order/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable String id){
        userService.cancelOrderById(id);
        return ResponseEntity.ok("successful");
    }

    @GetMapping("/get-all-orders/{id}")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable String id){
        List<Order> allSenderOrder = userService.getAllSendersOrder(id);
        return ResponseEntity.ok(allSenderOrder);
    }

    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<String> deleteOrders(@PathVariable String id){
        userService.deleteOrderById(id);
        return ResponseEntity.ok("successful");
    }

}
