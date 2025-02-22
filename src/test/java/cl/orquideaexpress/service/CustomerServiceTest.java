package cl.orquideaexpress.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.orquideaexpress.entity.Customer;
import cl.orquideaexpress.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFirstName("John");
        testCustomer.setLastName("Doe");
        testCustomer.setEmail("john.doe@example.com");
        testCustomer.setPhoneNumber("123456789");
    }

    @Test
    void findAll_ShouldReturnListOfCustomers() {
        // Arrange
        List<Customer> expectedCustomers = Arrays.asList(testCustomer);
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        // Act
        List<Customer> actualCustomers = customerService.findAll();

        // Assert
        assertThat(actualCustomers).isNotEmpty();
        assertThat(actualCustomers).hasSize(1);
        assertThat(actualCustomers.get(0).getFirstName()).isEqualTo("John");
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenCustomerExists_ShouldReturnCustomer() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        // Act
        Optional<Customer> result = customerService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getFirstName()).isEqualTo("John");
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenCustomerDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> result = customerService.findById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(customerRepository, times(1)).findById(999L);
    }

    @Test
    void save_ShouldReturnSavedCustomer() {
        // Arrange
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        Customer savedCustomer = customerService.save(testCustomer);

        // Assert
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getFirstName()).isEqualTo("John");
        verify(customerRepository, times(1)).save(testCustomer);
    }

    @Test
    void update_WhenCustomerExists_ShouldReturnUpdatedCustomer() {
        // Arrange
        Customer updatedDetails = new Customer();
        updatedDetails.setFirstName("Jane");
        updatedDetails.setEmail("jane.doe@example.com");
        updatedDetails.setPhoneNumber("987654321");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedDetails);

        // Act
        Customer updatedCustomer = customerService.update(1L, updatedDetails);

        // Assert
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getFirstName()).isEqualTo("Jane");
        assertThat(updatedCustomer.getEmail()).isEqualTo("jane.doe@example.com");
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void deleteById_ShouldInvokeRepositoryDelete() {
        // Arrange
        doNothing().when(customerRepository).deleteById(1L);

        // Act
        customerService.deleteById(1L);

        // Assert
        verify(customerRepository, times(1)).deleteById(1L);
    }
} 