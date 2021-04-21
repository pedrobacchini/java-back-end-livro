package com.santana.java.back.end.converter;

import com.santana.java.back.end.dto.CategoryDTO;
import com.santana.java.back.end.dto.ProductDTO;
import com.santana.java.back.end.model.Category;
import com.santana.java.back.end.model.Product;

public class DTOConverter {

    public static CategoryDTO convert(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public static ProductDTO convert(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setProductIdentifier(product.getProductIdentifier());
        if (product.getCategory() != null) {
            productDTO.setCategory(DTOConverter.convert(product.getCategory()));
        }
        return productDTO;
    }

}
