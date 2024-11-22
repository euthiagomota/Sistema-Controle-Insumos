package br.com.SistemaControleInsumos.controllers;


import br.com.SistemaControleInsumos.dtos.buyOrder.RequestBuyOrderDto;
import br.com.SistemaControleInsumos.dtos.buyOrder.ResponseBuyOrderDto;
import br.com.SistemaControleInsumos.services.BuyOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buy-orders")
@Tag(name = "buy order", description = "this routes is to control of buy orders.")
public class BuyOrderController {

    @Autowired
    BuyOrderService buyOrderService;

    @Operation(summary = "create buy order", description = "This route is to create buy order")
    @ApiResponse(responseCode = "201", description = "Success to create buy order")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/register")
    public ResponseEntity<ResponseBuyOrderDto> createBuyOrder(@RequestBody RequestBuyOrderDto request) {
       ResponseBuyOrderDto response = this.buyOrderService.createBuyOrder(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
