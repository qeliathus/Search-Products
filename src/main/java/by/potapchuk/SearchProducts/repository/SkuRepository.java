package by.potapchuk.SearchProducts.repository;

import by.potapchuk.SearchProducts.core.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {
}
