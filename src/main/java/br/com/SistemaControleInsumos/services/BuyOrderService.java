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
}