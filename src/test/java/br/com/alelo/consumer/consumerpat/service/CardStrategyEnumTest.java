package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardStrategyEnumTest {

    private Consumer consumer;

    @BeforeEach
    public void setUp() {
        consumer = new Consumer();
    }

    @Test
    public void testGetCardStrategyBalance_FoodCard() {
        consumer.setFoodCardNumber(1234);
        assertEquals(CardStrategyEnum.FOOD_CARD, CardStrategyEnum.getCardStrategyBalance(consumer));
    }

    @Test
    public void testGetCardStrategyBalance_DrugstoreCard() {
        consumer.setDrugstoreNumber(1234);
        assertEquals(CardStrategyEnum.DRUGSTORE_CARD, CardStrategyEnum.getCardStrategyBalance(consumer));
    }

    @Test
    public void testGetCardStrategyBalance_FuelCard() {
        consumer.setFuelCardNumber(1234);
        assertEquals(CardStrategyEnum.FUEL_CARD, CardStrategyEnum.getCardStrategyBalance(consumer));
    }

    @Test
    public void testGetCardStrategyBalance_DefaultFuelCard(){
        assertEquals(CardStrategyEnum.FUEL_CARD, CardStrategyEnum.getCardStrategyBalance(consumer));
    }

    @Test
    public void testGetCardStrategyByEstablishmentType_FoodCard() {
        assertEquals(CardStrategyEnum.FOOD_CARD, CardStrategyEnum.getCardStrategyByEstablishmentType(1));
    }

    @Test
    public void testGetCardStrategyByEstablishmentType_DrugstoreCard() {
        assertEquals(CardStrategyEnum.DRUGSTORE_CARD, CardStrategyEnum.getCardStrategyByEstablishmentType(2));
    }

    @Test
    public void testGetCardStrategyByEstablishmentType_FuelCard() {
        assertEquals(CardStrategyEnum.FUEL_CARD, CardStrategyEnum.getCardStrategyByEstablishmentType(3));
    }

    @Test
    public void testGetCardStrategyByEstablishmentType_Invalid() {
        assertThrows(RuntimeException.class, () -> CardStrategyEnum.getCardStrategyByEstablishmentType(4));
    }
}
