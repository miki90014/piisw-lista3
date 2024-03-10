package com.example.orderhistory;

import com.example.orderhistory.model.OrderHistory;
import com.example.orderhistory.model.OrdersDTO;
import com.example.orderhistory.service.OrderHistoryRepository;
import com.example.orderhistory.service.OrderHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderHistoryApplicationTests {

	@Autowired
	MockMvc mockMvc;
	@MockBean
	OrderHistoryRepository orderHistoryRepository;
	@MockBean
	OrderHistoryService orderHistoryService;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testGetAllOrders() throws Exception {
		OrderHistory order1 = new OrderHistory();
		order1.setOrderId(1L);
		OrderHistory order2 = new OrderHistory();
		order2.setOrderId(2L);

		List<OrderHistory> orderList = Arrays.asList(order1, order2);

		when(orderHistoryService.getAll()).thenReturn(orderList);

		mockMvc.perform(get("/orderHistory")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].orderId").value(1))
				.andExpect(jsonPath("$[1].orderId").value(2));
	}

	@Test
	public void testGetOrderHistory() throws Exception {
		OrderHistory order = new OrderHistory();
		order.setOrderId(1L);
		order.setCurierName("Test");

		when(orderHistoryService.getById(1L)).thenReturn(order);

		mockMvc.perform(get("/orderHistory/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.orderId").value(1))
				.andExpect(jsonPath("$.curierName").value("Test"));
	}

	/*@Test
	public void testAddOrder() throws Exception {
		OrdersDTO ordersDTO = new OrdersDTO();
		ordersDTO.setCustomerName("John Doe");

		mockMvc.perform(MockMvcRequestBuilders.post("/orderHistory/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("customerName", "")
				.param("curierName", "")
				.param("deliveryStatus", "")
				.param("items", "")
		).andExpect(status().isOk());
	}*/

	@Test
	public void testUpdateDelivery() throws Exception {
		OrderHistory order = new OrderHistory();
		order.setOrderId(1L);
		order.setCurierName("Test");

		when(orderHistoryService.getById(1L)).thenReturn(order);

		mockMvc.perform(get("/orderHistory/{delivery}/{id}", "Delivered", 1))
				.andExpect(status().isOk());

	}

}
