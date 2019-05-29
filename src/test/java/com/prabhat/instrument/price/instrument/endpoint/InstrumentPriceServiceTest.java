package com.prabhat.instrument.price.instrument.endpoint;

import com.prabhat.instrument.price.instrument.storage.Instrument;
import com.prabhat.instrument.price.instrument.storage.InstrumentPriceStorage;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InstrumentPriceServiceTest {
  @Mock
  InstrumentPriceStorage instrumentPriceStorage;

  @Test
  public void testGetInstrument() {
    // GIVEN
    final Instrument expected = Instrument.builder()
        .id("a233")
        .price(1.34f)
        .asOf(System.currentTimeMillis())
        .build();
    when(instrumentPriceStorage.get(anyString())).thenReturn(expected);
    final InstrumentPriceService instrumentPriceService = new InstrumentPriceService(instrumentPriceStorage);
    // WHEN

    Instrument actual = instrumentPriceService.getInstrument("a233");
    // THEN
    assertEquals(expected, actual);
    Mockito.verify(instrumentPriceStorage).get("a233");
  }

  @Test
  public void testGetAll() {
    // GIVEN
    final List<String> expected = Lists.newArrayList("124", "345");
    when(instrumentPriceStorage.getAll()).thenReturn(expected);
    final InstrumentPriceService instrumentPriceService = new InstrumentPriceService(instrumentPriceStorage);

    // WHEN
    List<String > actual = instrumentPriceService.getAll();
    // THEN
    assertEquals(expected, actual);
    Mockito.verify(instrumentPriceStorage).getAll();
  }
}