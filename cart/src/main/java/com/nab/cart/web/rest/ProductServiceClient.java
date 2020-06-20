package com.nab.cart.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;

import com.nab.cart.client.AuthorizedFeignClient;
import com.nab.cart.service.dto.Product;

@AuthorizedFeignClient(name = "product")
interface ProductServiceClient {
	
  @RequestMapping(value = "/api/products")
  Product[] getProductFromProductService();
  
}