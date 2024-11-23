package br.com.SistemaControleInsumos.services;

import br.com.SistemaControleInsumos.dtos.buyOrder.RequestBuyOrderDto;
import br.com.SistemaControleInsumos.dtos.buyOrder.ResponseBuyOrderDto;
import br.com.SistemaControleInsumos.dtos.products.RequestProductBuyOrderDto;
import br.com.SistemaControleInsumos.entities.BuyOrder;
import br.com.SistemaControleInsumos.entities.Product;
import br.com.SistemaControleInsumos.entities.Supplier;
import br.com.SistemaControleInsumos.entities.User;
import br.com.SistemaControleInsumos.repositories.BuyOrderRepository;
import br.com.SistemaControleInsumos.repositories.ProductRepository;
import br.com.SistemaControleInsumos.repositories.SupplierRepository;
import br.com.SistemaControleInsumos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuyOrderService {

    @Autowired
    BuyOrderRepository buyOrderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    ProductRepository productRepository;

    public ResponseBuyOrderDto createBuyOrder(RequestBuyOrderDto request) {

        Optional<User> optionalUser = this.userRepository.findById(request.createdBy());

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        }

       Optional<Supplier> supplier = this.supplierRepository.findById(request.supplierId());

        if (supplier.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Supplier not found!");
        }

        List<Long> productIdsRequest  = request.products().stream()
                .map(RequestProductBuyOrderDto::id)
                .toList();

        List<Product> products = this.productRepository.findAllById(productIdsRequest);

        System.out.println(products);

        if (products.size() != productIdsRequest.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more products not found!");
        }
        for (Product product : products) {

            if (!product.getActive()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Product " + product.getId() + " is inactive!");
            }
        }

        BuyOrder buyOrder = new BuyOrder(
                products,
                request.createdBy(),
                request.supplierId(),
                request.status(),
                request.expectedDeliveryDate()
        );
        BuyOrder buyOrderSaved = this.buyOrderRepository.save(buyOrder);

        ResponseBuyOrderDto response = new ResponseBuyOrderDto(
                buyOrderSaved.getId(),
                buyOrderSaved.getCreatedBy(),
                buyOrderSaved.getSupplierId(),
                buyOrderSaved.getStatus(),
                buyOrderSaved.getExpectedDeliveryDate(),
                buyOrderSaved.getCreatedAt(),
                buyOrderSaved.getProducts()
        );

        return response;
    }

    public List<ResponseBuyOrderDto> findAll(){
        try {
            List<BuyOrder> buyorders = this.buyOrderRepository.findAll();

            List<ResponseBuyOrderDto> responseDtos = new ArrayList<>();
            for (BuyOrder buyOrder : buyorders){
                ResponseBuyOrderDto responseBuyOrderDto = new ResponseBuyOrderDto(
                        buyOrder.getId(),
                        buyOrder.getCreatedAt(),
                        buyOrder.getStatus(),
                        buyOrder.getExpectedDeliveryDate(),
                        buyOrder.getSupplierId(),
                        buyOrder.getCreatedBy()

                );
                responseDtos.add(responseBuyOrderDto);
            }
            return responseDtos;
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the BuyOrder", e);
        }
    }

    public boolean delete(long id){
        try{
            Optional<BuyOrder> OptionalBuyOrder = this.buyOrderRepository.findById(id);
            if (OptionalBuyOrder.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"not found!");
            }
            this.buyOrderRepository.delete(OptionalBuyOrder.get());
            return true;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while deleting the Buy Order");
        }
    }

    public ResponseBuyOrderDto findById(long id){
        try{
            Optional<BuyOrder> OptionalBuyOrder = this.buyOrderRepository.findById(id);
            if (OptionalBuyOrder.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BuyOrder not found!");
            }
            BuyOrder buyOrder = OptionalBuyOrder.get();

            ResponseBuyOrderDto response = new ResponseBuyOrderDto(
                    buyOrder.getId(),
                    buyOrder.getCreatedAt(),
                    buyOrder.getStatus(),
                    buyOrder.getExpectedDeliveryDate(),
                    buyOrder.getSupplierId(),
                    buyOrder.getCreatedBy()
            );
            return response;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding Buy Order");
        }
    }

    public List<ResponseBuyOrderDto> dateFilter(Timestamp initialDate, Timestamp finishDate){
        try{
            List<BuyOrder> buyOrders = this.buyOrderRepository.findByCreatedAtBetween(
                    initialDate,
                    finishDate
            );
            List<ResponseBuyOrderDto> responseDtos = new ArrayList<>();

            for(BuyOrder buyOrder : buyOrders){
                ResponseBuyOrderDto responseBuyOrderDto = new ResponseBuyOrderDto(
                        buyOrder.getId(),
                        buyOrder.getCreatedAt(),
                        buyOrder.getStatus(),
                        buyOrder.getExpectedDeliveryDate(),
                        buyOrder.getSupplierId(),
                        buyOrder.getCreatedBy()
                );
                responseDtos.add(responseBuyOrderDto);
            }
            return responseDtos;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding Buy Order by date", e);
        }
    }
}
