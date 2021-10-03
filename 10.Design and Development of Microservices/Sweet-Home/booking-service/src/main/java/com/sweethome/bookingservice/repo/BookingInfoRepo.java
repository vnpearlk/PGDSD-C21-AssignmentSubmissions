package com.sweethome.bookingservice.repo;

import com.sweethome.bookingservice.entity.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository class. Using JpaRepository, to take advantage of both CRUD & results paging features inherited
 * by JpaRepository, from CrudRepository & PagingAndSortingRepository.
 */
public interface BookingInfoRepo extends JpaRepository<BookingInfoEntity, Integer> {
}
