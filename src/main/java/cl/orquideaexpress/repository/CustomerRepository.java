package cl.orquideaexpress.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.orquideaexpress.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}