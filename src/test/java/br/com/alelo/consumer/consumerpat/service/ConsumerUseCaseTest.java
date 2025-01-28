package br.com.alelo.consumer.consumerpat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.entity.Extract;
import br.com.alelo.consumer.consumerpat.respository.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.respository.ExtractRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class ConsumerUseCaseTest {

    @Mock
    private ConsumerRepository repository;

    @Mock
    private ExtractRepository extractRepository;

    @InjectMocks
    private ConsumerUseCase consumerUseCase;

    private Consumer consumer;

    @BeforeEach
    public void setUp() {
        consumer = new Consumer();
    }

    @Test
    public void testListAllConsumers() {
        List<Consumer> consumers = Arrays.asList(new Consumer(), new Consumer());
        when(repository.getAllConsumersList()).thenReturn(consumers);

        List<Consumer> result = consumerUseCase.listAllConsumers();
        assertEquals(2, result.size());
        verify(repository).getAllConsumersList();
    }

    @Test
    public void testUpdateConsumer() {
        consumerUseCase.updateConsumer(consumer);
        verify(repository).save(consumer);
    }

    @Test
    public void testCreateConsumer() {
        consumerUseCase.createConsumer(consumer);
        verify(repository).save(consumer);
    }

    @Test
    public void testFindConsumerByCard() {
        when(repository.findByAnyCardNumber(1234)).thenReturn(consumer);

        Consumer result = consumerUseCase.findConsumerByCard(1234);
        assertEquals(consumer, result);
        verify(repository).findByAnyCardNumber(1234);
    }

    @Test
    public void testSetBalance() {
        consumer.setFoodCardNumber(1234);
        consumer.setFoodCardBalance(100.00);
        when(repository.findByAnyCardNumber(1234)).thenReturn(consumer);

        consumerUseCase.setBalance(1234, 100.0);

        assertEquals(200.0, consumer.getFoodCardBalance());
        verify(repository).save(consumer);
    }

    @Test
    public void testBuy_FoodCard() {
        consumer.setFoodCardNumber(1234);
        consumer.setFoodCardBalance(100.00);
        when(repository.findByAnyCardNumber(1234)).thenReturn(consumer);

        consumerUseCase.buy(1, "Restaurant", 1234, "Lunch", 100.0);

        assertEquals(10.0, consumer.getFoodCardBalance());  // 10% de cashback
        verify(repository).save(consumer);
        verify(extractRepository).save(any(Extract.class));
    }

    @Test
    public void testBuy_DrugstoreCard() {
        consumer.setDrugstoreNumber(1234);
        consumer.setDrugstoreCardBalance(100.00);
        when(repository.findByAnyCardNumber(1234)).thenReturn(consumer);

        consumerUseCase.buy(2, "Pharmacy", 1234, "Medicine", 50.0);

        assertEquals(50.0, consumer.getDrugstoreCardBalance());
        verify(repository).save(consumer);
        verify(extractRepository).save(any(Extract.class));
    }

    @Test
    public void testBuy_FuelCard() {
        consumer.setFuelCardNumber(1234);
        consumer.setFuelCardBalance(100.00);
        when(repository.findByAnyCardNumber(1234)).thenReturn(consumer);

        consumerUseCase.buy(3, "Gas Station", 1234, "Fuel", 200.0);

        assertEquals(-170.0, consumer.getFuelCardBalance());  // 35% de acréscimo
        verify(repository).save(consumer);
        verify(extractRepository).save(any(Extract.class));
    }
}
