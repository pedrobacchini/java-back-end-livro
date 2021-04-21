package com.santana.java.back.end.repository;

import com.santana.java.back.end.dto.ShopReportDTO;
import com.santana.java.back.end.model.Shop;

import java.util.Date;
import java.util.List;

public interface ReportRepository {

    List<Shop> getShopByFilters(Date dataInicio, Date dataFim, Float valorMinimo);

    ShopReportDTO getReportByDate(Date dataInicio, Date dataFim);

}
