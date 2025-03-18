package org.shavneva.familybudget.repository;

import org.shavneva.familybudget.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.user.iduser = :userId AND FUNCTION('DATE_FORMAT', t.date, '%Y-%m') = :yearMonth")
    List<Transaction> findByUserAndMonth(@Param("userId") Integer userId, @Param("yearMonth") String yearMonth);

}
