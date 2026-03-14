package com.cloudbilling.billingsystem.controller;

import com.cloudbilling.billingsystem.dto.InvoiceDTO;
import com.cloudbilling.billingsystem.model.Invoice;
import com.cloudbilling.billingsystem.service.InvoiceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // ADMIN: create invoice for a user
    @PostMapping("/user/{userId}")
public InvoiceDTO createInvoiceForUser(@PathVariable Long userId,
                                       @RequestBody Invoice invoice) {
    return invoiceService.createInvoice(userId, invoice);
}

@GetMapping("/me")
public List<InvoiceDTO> getMyInvoices(Authentication authentication) {
    String email = authentication.getName();
    return invoiceService.getInvoicesForUser(email);
}

@GetMapping("/all")
public List<InvoiceDTO> getAllInvoices() {
    return invoiceService.getAllInvoices();
}
}