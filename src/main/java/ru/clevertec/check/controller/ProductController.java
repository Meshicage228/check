package ru.clevertec.check.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.service.impl.ProductServiceImpl;
import ru.clevertec.check.utils.InputDataValidator;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final InputDataValidator validator;

    @GetMapping
    public ResponseEntity<ProductDto> getById(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> saveProduct(@RequestBody ProductDto dto) throws BadRequestException {
        validator.validateProductDto(dto);
        productService.save(dto);

        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> putUpdate(@RequestBody ProductDto dto,
                                          @RequestParam("id") Integer id) throws BadRequestException {
        validator.validateProductDto(dto);
        productService.fullUpdateProduct(dto, id);

        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        productService.getById(id);
        productService.deleteById(id);

        return ResponseEntity.status(ACCEPTED).build();
    }
}
