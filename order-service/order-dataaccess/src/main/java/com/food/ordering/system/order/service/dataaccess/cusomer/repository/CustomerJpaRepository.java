package com.food.ordering.system.order.service.dataaccess.cusomer.repository;

import com.food.ordering.system.order.service.dataaccess.cusomer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
}
