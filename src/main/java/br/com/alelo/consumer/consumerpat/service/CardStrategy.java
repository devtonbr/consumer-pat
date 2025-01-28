package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Consumer;

public interface CardStrategy {

   void setBalance(Consumer consumer, double value);

   void buy(Consumer consumer, double value);
}
