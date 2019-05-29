package com.prabhat.instrument.price.instrument.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prabhat.instrument.price.instrument.storage.Instrument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InstrumentEndPoint.class)
public class InstrumentEndPointTest {
  @MockBean
  private InstrumentPriceService instrumentPriceService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetInstrument() throws Exception {
    // GIVEN
    final String id = "anId";
    final float price = 1.23f;
    final long asOf = System.currentTimeMillis();
    final  Instrument instrument = Instrument.builder()
        .id(id)
        .price(price)
        .asOf(asOf)
        .build();
    when(instrumentPriceService.getInstrument(id)).thenReturn(instrument);
    // WHEN
    mockMvc.perform(
        get("/instrument/anId")

    ).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(instrument)));
    // THEN
  }
}