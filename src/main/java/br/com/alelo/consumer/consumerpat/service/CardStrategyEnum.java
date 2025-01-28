package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Consumer;

public enum CardStrategyEnum implements CardStrategy {

        FOOD_CARD {
            @Override
            public void setBalance(Consumer consumer, double value) {
                // é cartão de refeição
                consumer.setFoodCardBalance(consumer.getFoodCardBalance() + value);
            }

            @Override
            public void buy(Consumer consumer, double value) {
                // Para compras no cartão de alimentação o cliente recebe um desconto de 10%
                Double cashback  = (value / 100) * 10;
                value = value - cashback;
                consumer.setFoodCardBalance(consumer.getFoodCardBalance() - value);
            }
        },
        FUEL_CARD {
            @Override
            public void setBalance(Consumer consumer, double value) {
                // É cartão de combustivel
                consumer.setFuelCardBalance(consumer.getFuelCardBalance() + value);
            }

            @Override
            public void buy(Consumer consumer, double value) {
                // Nas compras com o cartão de combustivel existe um acrescimo de 35%;
                Double tax  = (value / 100) * 35;
                value = value + tax;
                consumer.setFuelCardBalance(consumer.getFuelCardBalance() - value);
            }
        },
        DRUGSTORE_CARD {
            @Override
            public void setBalance(Consumer consumer, double value) {
                // é cartão de farmácia
                consumer.setDrugstoreCardBalance(consumer.getDrugstoreCardBalance() + value);
            }

            @Override
            public void buy(Consumer consumer, double value) {
                consumer.setDrugstoreCardBalance(consumer.getDrugstoreCardBalance() - value);
            }
        };

    public static CardStrategy getCardStrategyByEstablishmentType(int establishmentType) {
        CardStrategy cardStrategy;
        switch (establishmentType) {
            case 1:
                cardStrategy = CardStrategyEnum.FOOD_CARD;
                break;
            case 2:
                cardStrategy = CardStrategyEnum.DRUGSTORE_CARD;
                break;
            case 3:
                cardStrategy = CardStrategyEnum.FUEL_CARD;
                break;
            default:
                throw new RuntimeException("CARD NOT FOUND");
        }
        return cardStrategy;
    }

    public static CardStrategy getCardStrategyBalance(Consumer consumer) {
        CardStrategy cardStrategy;
        if (consumer.getDrugstoreNumber() != 0) {
            cardStrategy = CardStrategyEnum.DRUGSTORE_CARD;
        } else if (consumer.getFoodCardNumber() != 0) {
            cardStrategy = CardStrategyEnum.FOOD_CARD;
        } else {
            cardStrategy = CardStrategyEnum.FUEL_CARD;
        }
        return cardStrategy;
    }




}
