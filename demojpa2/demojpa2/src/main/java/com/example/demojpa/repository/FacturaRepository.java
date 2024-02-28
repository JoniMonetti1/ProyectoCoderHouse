package com.example.demojpa.repository;

import com.example.demojpa.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
