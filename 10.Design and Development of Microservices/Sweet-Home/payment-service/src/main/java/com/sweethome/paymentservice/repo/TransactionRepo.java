package com.sweethome.paymentservice.repo;

import com.sweethome.paymentservice.entity.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository class. Using JpaRepository, to take advantage of both CRUD & results paging features inherited
 * by JpaRepository, from CrudRepository & PagingAndSortingRepository.
 */
public interface TransactionRepo extends JpaRepository<TransactionDetailsEntity, Integer> {
}
