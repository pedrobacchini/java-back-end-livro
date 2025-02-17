package com.santana.java.back.end.controller;

import com.santana.java.back.end.dto.ShopDTO;
import com.santana.java.back.end.dto.ShopReportDTO;
import com.santana.java.back.end.service.ShopService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shopping/")
    public List<ShopDTO> getShops() {
        return shopService.getAll();
    }

    @GetMapping("/shopping/shopByUser/{userIdentifier}")
    public List<ShopDTO> getShops(@PathVariable String userIdentifier) {
        return shopService.getByUser(userIdentifier);
    }

    @GetMapping("/shopping/shopByDate")
    public List<ShopDTO> getShops(@RequestBody ShopDTO shopDTO) {
        return shopService.getByDate(shopDTO);
    }

    @GetMapping("/shopping/{id}")
    public ShopDTO findById(@PathVariable Long id) {
        return shopService.findById(id);
    }

    @PostMapping("/shopping/")
    public ShopDTO newShop(
            @RequestHeader(name = "key") String key,
            @RequestBody ShopDTO shopDTO) {
        return shopService.save(shopDTO, key);
    }

    @GetMapping("/shopping/search")
    public List<ShopDTO> getShopsByFilter(
            @RequestParam(name = "startDate")
            @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate,
            @RequestParam(name = "minimumValue", required = false) Float minimumValue) {
        return shopService.getShopsByFilter(startDate, endDate, minimumValue);
    }

    @GetMapping("/shopping/report")
    public ShopReportDTO getReportByDate(
            @RequestParam(name = "startDate")
            @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
            @RequestParam(name = "endDate")
            @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {
        return shopService.getReportByDate(startDate, endDate);
    }

}
