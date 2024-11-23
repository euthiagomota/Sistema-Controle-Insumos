package br.com.SistemaControleInsumos.controllers;


import br.com.SistemaControleInsumos.dtos.buyOrder.RequestBuyOrderDto;
import br.com.SistemaControleInsumos.dtos.buyOrder.ResponseBuyOrderDto;
import br.com.SistemaControleInsumos.dtos.products.ResponseProductDto;
import br.com.SistemaControleInsumos.services.BuyOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

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

    @Operation(summary = "find all Buy order", description = "this route is to find all Buy order")
    @ApiResponse(responseCode = "200", description = "Success to find all Buy order")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/ListAll")
    public ResponseEntity<List<ResponseBuyOrderDto>> findAllBuyOrder() {
        List<ResponseBuyOrderDto> BuyOrder = this.buyOrderService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(BuyOrder);
    }

    @Operation(summary = "delete buy Order", description = "This route is to delete buy Order")
    @ApiResponse(responseCode = "200", description = "Success to delete buyOrder")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBuyOrder(@PathVariable long id) {
        Boolean isDeleted = this.buyOrderService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
    }

    @Operation(summary = "find Buy Order by id", description = "This route is to find Buy Order by id")
    @ApiResponse(responseCode = "200", description = "Success to find Buy Order by id")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBuyOrderDto> findBuyOrderById(@PathVariable long id) {
        ResponseBuyOrderDto buyOrder = this.buyOrderService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(buyOrder);
    }

    @Operation(summary = "date filter Buy Order", description = "This route is to filter Buy Order by date")
    @ApiResponse(responseCode = "200", description = "Success to filter Buy Order by date")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/date-filter")
    public ResponseEntity<List<ResponseBuyOrderDto>> dateFilter(
            @RequestParam("initialDate") String initialDateStr,
            @RequestParam("finishDate") String finishDateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Timestamp initialDate = new Timestamp(sdf.parse(initialDateStr).getTime());
            Timestamp finishDate = new Timestamp(sdf.parse(finishDateStr).getTime());
            List<ResponseBuyOrderDto> buyOrders = this.buyOrderService.dateFilter(initialDate, finishDate);
            return ResponseEntity.status(HttpStatus.OK).body(buyOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
