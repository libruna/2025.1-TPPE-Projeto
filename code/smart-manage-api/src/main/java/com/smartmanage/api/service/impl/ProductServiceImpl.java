package com.smartmanage.api.service.impl;

import com.smartmanage.api.dto.request.ProductRequestDto;
import com.smartmanage.api.dto.response.ProductResponseDto;
import com.smartmanage.api.exception.BusinessException;
import com.smartmanage.api.model.entity.Category;
import com.smartmanage.api.model.entity.Product;
import com.smartmanage.api.repository.CategoryRepository;
import com.smartmanage.api.repository.ProductRepository;
import com.smartmanage.api.service.ProductService;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = new ModelMapper();
        this.mapper.addConverter(new AbstractConverter<Product, ProductResponseDto>() {
            protected ProductResponseDto convert(Product product) {
                if (product != null) {
                    ProductResponseDto productResponse = new ProductResponseDto();
                    productResponse.setId(product.getId());
                    productResponse.setName(product.getName());
                    productResponse.setDescription(product.getDescription());
                    productResponse.setPrice(product.getPrice());
                    productResponse.setBarCode(product.getBarCode());
                    productResponse.setStock(product.getStock());

                    return productResponse;
                }
                return null;
            }
        });
    }

    @Override
    public ProductResponseDto saveProduct(ProductRequestDto productRequestDto) {
        Set<Category> categories = categoryRepository.findAllById(productRequestDto.getCategories());

        if (categories.size() != productRequestDto.getCategories().size()) {
            throw new BusinessException("Categoria não encontrada.", HttpStatus.NOT_FOUND);
        }

        Product product = mapper.map(productRequestDto, Product.class);
        product.setCategories(categories);

        return ProductResponseDto.builder()
                .id(productRepository.save(product).getId())
                .build();
    }

    @Override
    public Page<ProductResponseDto> getProducts(String search, Pageable pageable) {
        Page<Product> productsPage = productRepository.findByFilters(search, pageable);
        return productsPage.map(product -> mapper.map(product, ProductResponseDto.class));
    }

    @Override
    public ProductResponseDto getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado.", HttpStatus.NOT_FOUND));

        return mapper.map(product, ProductResponseDto.class);
    }
}
