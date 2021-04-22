package com.santana.java.back.end.service;

import com.santana.java.back.end.converter.DTOConverter;
import com.santana.java.back.end.dto.*;
import com.santana.java.back.end.exception.ProductNotFoundException;
import com.santana.java.back.end.model.Shop;
import com.santana.java.back.end.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final ProductService productService;
    private final UserService userService;

    public ShopService(ShopRepository shopRepository,
                       ProductService productService,
                       UserService userService) {
        this.shopRepository = shopRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<ShopDTO> getAll() {
        List<Shop> shops = shopRepository.findAll();
        return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public List<ShopDTO> getByUser(String userIdentifier) {
        List<Shop> shops = shopRepository.findAllByUserIdentifier(userIdentifier);
        return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public List<ShopDTO> getByDate(ShopDTO shopDTO) {
        List<Shop> shops = shopRepository.findAllByDateGreaterThan(shopDTO.getDate());
        return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public ShopDTO findById(long ProductId) {
        Optional<Shop> shop = shopRepository.findById(ProductId);
        if (shop.isPresent()) {
            return DTOConverter.convert(shop.get());
        }
        throw new ProductNotFoundException();
    }

    public ShopDTO save(ShopDTO shopDTO, String key) {
        UserDTO userDTO = userService.getUserByCpf(shopDTO.getUserIdentifier(), key);
        validateProducts(shopDTO.getItems());

        shopDTO.setTotal(shopDTO.getItems()
                .stream()
                .map(ItemDTO::getPrice)
                .reduce((float) 0, Float::sum));

        Shop shop = Shop.convert(shopDTO);
        shop.setDate(new Date());

        shop = shopRepository.save(shop);
        return DTOConverter.convert(shop);
    }

    private boolean validateProducts(List<ItemDTO> items) {
        for (ItemDTO item : items) {
            ProductDTO productDTO = productService.getProductByIdentifier(item.getProductIdentifier());
            if (productDTO == null) {
                return false;
            }
            item.setPrice(productDTO.getPrice());
        }
        return true;
    }

    public List<ShopDTO> getShopsByFilter(Date startDate, Date endDate, Float minimumValue) {
        List<Shop> shops = shopRepository.getShopByFilters(startDate, endDate, minimumValue);
        return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());

    }

    public ShopReportDTO getReportByDate(Date startDate, Date endDate) {
        return shopRepository.getReportByDate(startDate, endDate);
    }

}
