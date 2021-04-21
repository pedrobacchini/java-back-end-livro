package com.santana.java.back.end.repository;

import com.santana.java.back.end.dto.ShopReportDTO;
import com.santana.java.back.end.model.Shop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class ReportRepositoryImpl implements ReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Shop> getShopByFilters(Date startDate, Date endDate, Float minimumValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("select s ");
        sb.append("from shop s ");
        sb.append("where s.date >= :startDate ");

        if (endDate != null) {
            sb.append("and s.date <= :endDate ");
        }

        if (minimumValue != null) {
            sb.append("and s.total <= :minimumValue ");
        }

        TypedQuery<Shop> query = entityManager.createQuery(sb.toString(), Shop.class);
        query.setParameter("startDate", startDate);

        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }

        if (minimumValue != null) {
            query.setParameter("minimumValue", minimumValue);
        }
        return query.getResultList();
    }

    @Override
    public ShopReportDTO getReportByDate(Date startDate, Date endDate) {
        String queryString = "select count(sp.id), sum(sp.total), avg(sp.total) " +
                "from shopping.shop sp " +
                "where  sp.date >= :startDate " +
                "and sp.date <= :endDate ";
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        Object[] result = (Object[]) query.getSingleResult();
        ShopReportDTO shopReportDTO = new ShopReportDTO();
        shopReportDTO.setCount(((BigInteger) result[0]).intValue());
        shopReportDTO.setTotal((Double) result[1]);
        shopReportDTO.setMean((Double) result[2]);
        return shopReportDTO;
    }

}
