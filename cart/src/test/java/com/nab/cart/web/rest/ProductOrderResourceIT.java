package com.nab.cart.web.rest;

import com.nab.cart.CartApp;
import com.nab.cart.config.TestSecurityConfiguration;
import com.nab.cart.domain.ProductOrder;
import com.nab.cart.domain.OrderItem;
import com.nab.cart.repository.ProductOrderRepository;
import com.nab.cart.service.ProductOrderService;
import com.nab.cart.service.dto.ProductOrderCriteria;
import com.nab.cart.service.ProductOrderQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nab.cart.domain.enumeration.OrderStatus;
/**
 * Integration tests for the {@link ProductOrderResource} REST controller.
 */
@SpringBootTest(classes = { CartApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductOrderResourceIT {

    private static final Instant DEFAULT_PLACED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLACED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.PENDING;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.COMPLETED;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER = "BBBBBBBBBB";

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductOrderQueryService productOrderQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductOrderMockMvc;

    private ProductOrder productOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOrder createEntity(EntityManager em) {
        ProductOrder productOrder = new ProductOrder()
            .placedDate(DEFAULT_PLACED_DATE)
            .status(DEFAULT_STATUS)
            .code(DEFAULT_CODE)
            .customer(DEFAULT_CUSTOMER);
        return productOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOrder createUpdatedEntity(EntityManager em) {
        ProductOrder productOrder = new ProductOrder()
            .placedDate(UPDATED_PLACED_DATE)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE)
            .customer(UPDATED_CUSTOMER);
        return productOrder;
    }

    @BeforeEach
    public void initTest() {
        productOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOrder() throws Exception {
        int databaseSizeBeforeCreate = productOrderRepository.findAll().size();
        // Create the ProductOrder
        restProductOrderMockMvc.perform(post("/api/product-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isCreated());

        // Validate the ProductOrder in the database
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOrder testProductOrder = productOrderList.get(productOrderList.size() - 1);
        assertThat(testProductOrder.getPlacedDate()).isEqualTo(DEFAULT_PLACED_DATE);
        assertThat(testProductOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductOrder.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductOrder.getCustomer()).isEqualTo(DEFAULT_CUSTOMER);
    }

    @Test
    @Transactional
    public void createProductOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOrderRepository.findAll().size();

        // Create the ProductOrder with an existing ID
        productOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOrderMockMvc.perform(post("/api/product-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOrder in the database
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPlacedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOrderRepository.findAll().size();
        // set the field null
        productOrder.setPlacedDate(null);

        // Create the ProductOrder, which fails.


        restProductOrderMockMvc.perform(post("/api/product-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOrderRepository.findAll().size();
        // set the field null
        productOrder.setStatus(null);

        // Create the ProductOrder, which fails.


        restProductOrderMockMvc.perform(post("/api/product-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOrderRepository.findAll().size();
        // set the field null
        productOrder.setCode(null);

        // Create the ProductOrder, which fails.


        restProductOrderMockMvc.perform(post("/api/product-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOrderRepository.findAll().size();
        // set the field null
        productOrder.setCustomer(null);

        // Create the ProductOrder, which fails.


        restProductOrderMockMvc.perform(post("/api/product-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOrders() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList
        restProductOrderMockMvc.perform(get("/api/product-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].placedDate").value(hasItem(DEFAULT_PLACED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER)));
    }
    
    @Test
    @Transactional
    public void getProductOrder() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get the productOrder
        restProductOrderMockMvc.perform(get("/api/product-orders/{id}", productOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productOrder.getId().intValue()))
            .andExpect(jsonPath("$.placedDate").value(DEFAULT_PLACED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.customer").value(DEFAULT_CUSTOMER));
    }


    @Test
    @Transactional
    public void getProductOrdersByIdFiltering() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        Long id = productOrder.getId();

        defaultProductOrderShouldBeFound("id.equals=" + id);
        defaultProductOrderShouldNotBeFound("id.notEquals=" + id);

        defaultProductOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultProductOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductOrderShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductOrdersByPlacedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where placedDate equals to DEFAULT_PLACED_DATE
        defaultProductOrderShouldBeFound("placedDate.equals=" + DEFAULT_PLACED_DATE);

        // Get all the productOrderList where placedDate equals to UPDATED_PLACED_DATE
        defaultProductOrderShouldNotBeFound("placedDate.equals=" + UPDATED_PLACED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByPlacedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where placedDate not equals to DEFAULT_PLACED_DATE
        defaultProductOrderShouldNotBeFound("placedDate.notEquals=" + DEFAULT_PLACED_DATE);

        // Get all the productOrderList where placedDate not equals to UPDATED_PLACED_DATE
        defaultProductOrderShouldBeFound("placedDate.notEquals=" + UPDATED_PLACED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByPlacedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where placedDate in DEFAULT_PLACED_DATE or UPDATED_PLACED_DATE
        defaultProductOrderShouldBeFound("placedDate.in=" + DEFAULT_PLACED_DATE + "," + UPDATED_PLACED_DATE);

        // Get all the productOrderList where placedDate equals to UPDATED_PLACED_DATE
        defaultProductOrderShouldNotBeFound("placedDate.in=" + UPDATED_PLACED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByPlacedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where placedDate is not null
        defaultProductOrderShouldBeFound("placedDate.specified=true");

        // Get all the productOrderList where placedDate is null
        defaultProductOrderShouldNotBeFound("placedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductOrdersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where status equals to DEFAULT_STATUS
        defaultProductOrderShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the productOrderList where status equals to UPDATED_STATUS
        defaultProductOrderShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where status not equals to DEFAULT_STATUS
        defaultProductOrderShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the productOrderList where status not equals to UPDATED_STATUS
        defaultProductOrderShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultProductOrderShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the productOrderList where status equals to UPDATED_STATUS
        defaultProductOrderShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where status is not null
        defaultProductOrderShouldBeFound("status.specified=true");

        // Get all the productOrderList where status is null
        defaultProductOrderShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductOrdersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where code equals to DEFAULT_CODE
        defaultProductOrderShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the productOrderList where code equals to UPDATED_CODE
        defaultProductOrderShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where code not equals to DEFAULT_CODE
        defaultProductOrderShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the productOrderList where code not equals to UPDATED_CODE
        defaultProductOrderShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProductOrderShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the productOrderList where code equals to UPDATED_CODE
        defaultProductOrderShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where code is not null
        defaultProductOrderShouldBeFound("code.specified=true");

        // Get all the productOrderList where code is null
        defaultProductOrderShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductOrdersByCodeContainsSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where code contains DEFAULT_CODE
        defaultProductOrderShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the productOrderList where code contains UPDATED_CODE
        defaultProductOrderShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where code does not contain DEFAULT_CODE
        defaultProductOrderShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the productOrderList where code does not contain UPDATED_CODE
        defaultProductOrderShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllProductOrdersByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where customer equals to DEFAULT_CUSTOMER
        defaultProductOrderShouldBeFound("customer.equals=" + DEFAULT_CUSTOMER);

        // Get all the productOrderList where customer equals to UPDATED_CUSTOMER
        defaultProductOrderShouldNotBeFound("customer.equals=" + UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByCustomerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where customer not equals to DEFAULT_CUSTOMER
        defaultProductOrderShouldNotBeFound("customer.notEquals=" + DEFAULT_CUSTOMER);

        // Get all the productOrderList where customer not equals to UPDATED_CUSTOMER
        defaultProductOrderShouldBeFound("customer.notEquals=" + UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByCustomerIsInShouldWork() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where customer in DEFAULT_CUSTOMER or UPDATED_CUSTOMER
        defaultProductOrderShouldBeFound("customer.in=" + DEFAULT_CUSTOMER + "," + UPDATED_CUSTOMER);

        // Get all the productOrderList where customer equals to UPDATED_CUSTOMER
        defaultProductOrderShouldNotBeFound("customer.in=" + UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByCustomerIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where customer is not null
        defaultProductOrderShouldBeFound("customer.specified=true");

        // Get all the productOrderList where customer is null
        defaultProductOrderShouldNotBeFound("customer.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductOrdersByCustomerContainsSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where customer contains DEFAULT_CUSTOMER
        defaultProductOrderShouldBeFound("customer.contains=" + DEFAULT_CUSTOMER);

        // Get all the productOrderList where customer contains UPDATED_CUSTOMER
        defaultProductOrderShouldNotBeFound("customer.contains=" + UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    public void getAllProductOrdersByCustomerNotContainsSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList where customer does not contain DEFAULT_CUSTOMER
        defaultProductOrderShouldNotBeFound("customer.doesNotContain=" + DEFAULT_CUSTOMER);

        // Get all the productOrderList where customer does not contain UPDATED_CUSTOMER
        defaultProductOrderShouldBeFound("customer.doesNotContain=" + UPDATED_CUSTOMER);
    }


    @Test
    @Transactional
    public void getAllProductOrdersByOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);
        OrderItem orderItem = OrderItemResourceIT.createEntity(em);
        em.persist(orderItem);
        em.flush();
        productOrder.addOrderItem(orderItem);
        productOrderRepository.saveAndFlush(productOrder);
        Long orderItemId = orderItem.getId();

        // Get all the productOrderList where orderItem equals to orderItemId
        defaultProductOrderShouldBeFound("orderItemId.equals=" + orderItemId);

        // Get all the productOrderList where orderItem equals to orderItemId + 1
        defaultProductOrderShouldNotBeFound("orderItemId.equals=" + (orderItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductOrderShouldBeFound(String filter) throws Exception {
        restProductOrderMockMvc.perform(get("/api/product-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].placedDate").value(hasItem(DEFAULT_PLACED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER)));

        // Check, that the count call also returns 1
        restProductOrderMockMvc.perform(get("/api/product-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductOrderShouldNotBeFound(String filter) throws Exception {
        restProductOrderMockMvc.perform(get("/api/product-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOrderMockMvc.perform(get("/api/product-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductOrder() throws Exception {
        // Get the productOrder
        restProductOrderMockMvc.perform(get("/api/product-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOrder() throws Exception {
        // Initialize the database
        productOrderService.save(productOrder);

        int databaseSizeBeforeUpdate = productOrderRepository.findAll().size();

        // Update the productOrder
        ProductOrder updatedProductOrder = productOrderRepository.findById(productOrder.getId()).get();
        // Disconnect from session so that the updates on updatedProductOrder are not directly saved in db
        em.detach(updatedProductOrder);
        updatedProductOrder
            .placedDate(UPDATED_PLACED_DATE)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE)
            .customer(UPDATED_CUSTOMER);

        restProductOrderMockMvc.perform(put("/api/product-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductOrder)))
            .andExpect(status().isOk());

        // Validate the ProductOrder in the database
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeUpdate);
        ProductOrder testProductOrder = productOrderList.get(productOrderList.size() - 1);
        assertThat(testProductOrder.getPlacedDate()).isEqualTo(UPDATED_PLACED_DATE);
        assertThat(testProductOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductOrder.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductOrder.getCustomer()).isEqualTo(UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOrder() throws Exception {
        int databaseSizeBeforeUpdate = productOrderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOrderMockMvc.perform(put("/api/product-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOrder in the database
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductOrder() throws Exception {
        // Initialize the database
        productOrderService.save(productOrder);

        int databaseSizeBeforeDelete = productOrderRepository.findAll().size();

        // Delete the productOrder
        restProductOrderMockMvc.perform(delete("/api/product-orders/{id}", productOrder.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
