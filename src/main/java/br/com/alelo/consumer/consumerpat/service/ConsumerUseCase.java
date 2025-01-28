package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.entity.Extract;
import br.com.alelo.consumer.consumerpat.respository.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.respository.ExtractRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class ConsumerUseCase {

    @Autowired
    ExtractRepository extractRepository;

    @Autowired
    ConsumerRepository repository;

    public List<Consumer> listAllConsumers() {
        log.info("obtendo todos clientes");
        return repository.getAllConsumersList();
    }

    public void updateConsumer(Consumer consumer) {
        repository.save(consumer);
    }

    public void createConsumer(Consumer consumer) {
        repository.save(consumer);
    }

    public Consumer findConsumerByCard(int cardNumber) {
        return repository.findByAnyCardNumber(cardNumber);
    }

    public void setBalance(int cardNumber, double value) {

        var consumer = repository.findByAnyCardNumber(cardNumber);
        CardStrategy cardStrategy = CardStrategyEnum.getCardStrategyBalance(consumer);
        cardStrategy.setBalance(consumer,value);
        repository.save(consumer);
    }

    public void buy(int establishmentType, String establishmentName, int cardNumber, String productDescription, double value) {

        Consumer consumer = repository.findByAnyCardNumber(cardNumber);
        CardStrategy cardStrategy = CardStrategyEnum.getCardStrategyByEstablishmentType(establishmentType);
        cardStrategy.buy(consumer,value);
        repository.save(consumer);
        Extract extract = new Extract(establishmentName, productDescription, new Date(), cardNumber, value);
        extractRepository.save(extract);

    }

}
