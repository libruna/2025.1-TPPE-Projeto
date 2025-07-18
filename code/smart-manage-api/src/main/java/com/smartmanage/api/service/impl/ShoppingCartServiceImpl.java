package com.smartmanage.api.service.impl;

import com.smartmanage.api.dto.request.ItemRequestDto;
import com.smartmanage.api.dto.request.ShoppingCartRequestDto;
import com.smartmanage.api.dto.response.ShoppingCartResponseDto;
import com.smartmanage.api.model.entity.Client;
import com.smartmanage.api.model.entity.Product;
import com.smartmanage.api.model.entity.ShoppingCart;
import com.smartmanage.api.model.entity.ShoppingCartItem;
import com.smartmanage.api.repository.ClientRepository;
import com.smartmanage.api.repository.ProductRepository;
import com.smartmanage.api.repository.ShoppingCartRepository;
import com.smartmanage.api.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.mapper = new ModelMapper();
    }

    public void saveShoppingCart(UUID clientId, ShoppingCartRequestDto shoppingCartRequestDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

        ShoppingCart cart = new ShoppingCart();
        cart.setClient(client);
        cart.setItems(new HashSet<>());
        cart.getItems().add(buildShoppingCartItem(shoppingCartRequestDto.getItem(), cart));

        shoppingCartRepository.save(cart);
    }

    @Override
    public void updateShoppingCart(UUID clientId, ShoppingCartRequestDto shoppingCartRequestDto) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByClientId(clientId);

        if (shoppingCart.isEmpty()) {
            saveShoppingCart(clientId, shoppingCartRequestDto);
        } else {
            ShoppingCart cart = shoppingCart.get();

            Optional<ShoppingCartItem> existingItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(shoppingCartRequestDto.getItem().getProductId()))
                    .findFirst();

            if (existingItem.isPresent()) {
                existingItem.get().setNumber(existingItem.get().getNumber() + shoppingCartRequestDto.getItem().getNumber());
            } else {
                cart.getItems().add(buildShoppingCartItem(shoppingCartRequestDto.getItem(), cart));
            }

            shoppingCartRepository.save(cart);
        }
    }

    @Override
    public ShoppingCartResponseDto getShoppingCartByClientId(UUID clientId, Integer discountPercentage) {
        ShoppingCart cart = shoppingCartRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrinho não encontrado."));

        ShoppingCartResponseDto shoppingCartResponse = mapper.map(cart, ShoppingCartResponseDto.class);

        BigDecimal total = getTotalPrice(cart);

        if (discountPercentage != null && discountPercentage > 0) {
            BigDecimal percentage = BigDecimal.valueOf(discountPercentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            shoppingCartResponse.setTotalPrice(total.subtract(total.multiply(percentage)));
        } else {
            shoppingCartResponse.setTotalPrice(total);
        }

        return shoppingCartResponse;
    }

    private ShoppingCartItem buildShoppingCartItem(ItemRequestDto itemRequestDto, ShoppingCart cart) {
        Product product = productRepository.findById(itemRequestDto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));

        ShoppingCartItem item = new ShoppingCartItem();
        item.setShoppingCart(cart);
        item.setProduct(product);
        item.setNumber(itemRequestDto.getNumber());

        return item;
    }

    public BigDecimal getTotalPrice(ShoppingCart cart) {
        return cart.getItems().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProduct().getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

                    BigDecimal price = product.getPrice();
                    int number = item.getNumber();

                    return price.multiply(BigDecimal.valueOf(number));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
