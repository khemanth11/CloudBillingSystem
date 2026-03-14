package com.cloudbilling.billingsystem.repository;

import com.cloudbilling.billingsystem.model.Invoice;
import com.cloudbilling.billingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByUser(User user);

    List<Invoice> findByUserEmail(String email);
}