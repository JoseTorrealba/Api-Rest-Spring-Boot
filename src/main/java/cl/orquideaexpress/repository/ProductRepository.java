package cl.orquideaexpress.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.orquideaexpress.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
