package com.cloudbilling.billingsystem.service;

import java.util.stream.Collectors;
import com.cloudbilling.billingsystem.dto.InvoiceDTO;
import com.cloudbilling.billingsystem.model.Invoice;
import com.cloudbilling.billingsystem.model.User;
import com.cloudbilling.billingsystem.repository.InvoiceRepository;
import com.cloudbilling.billingsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
    }

    public InvoiceDTO createInvoice(Long userId, Invoice invoice) {
    User user = userRepository.findById(userId).orElseThrow();
    invoice.setUser(user);
    Invoice saved = invoiceRepository.save(invoice);
    return toDto(saved);
}

public List<InvoiceDTO> getInvoicesForUser(String email) {
    return invoiceRepository.findByUserEmail(email)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
}

public List<InvoiceDTO> getAllInvoices() {
    return invoiceRepository.findAll()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
}

    private InvoiceDTO toDto(Invoice invoice) {
    InvoiceDTO dto = new InvoiceDTO();
    dto.setId(invoice.getId());
    dto.setUserId(invoice.getUser().getId());
    dto.setUserEmail(invoice.getUser().getEmail());
    dto.setAmount(invoice.getAmount());
    dto.setCurrency(invoice.getCurrency());
    dto.setDescription(invoice.getDescription());
    dto.setStatus(invoice.getStatus());
    dto.setDueDate(invoice.getDueDate());
    dto.setCreatedAt(invoice.getCreatedAt());
    return dto;
}
}