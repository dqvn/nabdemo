# nabdemo

This is a project for nabdemo

# System Design Architect

From the requirements, I will create 4 main components:

	MS1: Eureka Registry
	MS2: Zuul Gateway & CMS Admin Portal
	MS3: Product Service
	MS4: Cart Service 

![System Design](https://github.com/dqvn/nabdemo/blob/master/imgs/System-Architect-a.png)

# Design ERD

Read requirements and do design & sperate components based on JDL-Studio
![Read requirements and do design compose on JDL-Studio](https://github.com/dqvn/nabdemo/blob/master/imgs/ERD_final.png)

# Modeling microservice entities in JDL: This is the full entities:

----------------------------------------------------------
	/** Product sold by the Online store */
	entity Product {
	    name String required
	    brand ProductBrand required
	    price BigDecimal required min(0)
	    productColor ProductColor required
	    productSize Size
	    status ProductStatus required
	}
	
	enum ProductColor {
	    RED, GREEN, YELLOW, GREY, WHITE, ORANGE
	}
	
	enum ProductBrand {
	    JUNO, LV, KAFPA, CROSS, NOUS, JK, ADV
	}
	
	enum ProductStatus {
	    CURRENT, OUTDATE
	}
	
	enum Size {
	    S, M, L, XL, XXL
	}
	
	entity ProductCategory {
	    name String required
	    description String
	}
	
	relationship OneToMany {
	   ProductCategory{product} to Product{productCategory(name)}
	}
	
	service Product, ProductCategory with serviceClass
	paginate Product, ProductCategory with pagination
	filter Product, ProductCategory
	microservice Product, ProductCategory with product
	
	entity ProductOrder {
	    placedDate Instant required
	    status OrderStatus required
	    code String required
	    customer String required
	}
	
	enum OrderStatus {
	    PENDING, COMPLETED, CANCELLED
	}
	
	entity OrderItem {
	    quantity Integer required min(0)
	    totalPrice BigDecimal required min(0)
	    status OrderItemStatus required
	    productName String required
	}
	
	enum OrderItemStatus {
	    AVAILABLE, OUT_OF_STOCK, BACK_ORDER
	}
	
	relationship OneToMany {
	   ProductOrder{orderItem} to OrderItem{order(code) required}
	}

# Generate project by JH

By my familiar with Microservices stacks and the freestyle requirement to use framework, thus I used the JH for quick creating project as image

![By my familiar with Microservices stacks and the freestyle requirement to use framework, thus I used the JH for quicly create project as image](https://github.com/dqvn/nabdemo/blob/master/imgs/JH-Project-structure.png)

# Project Build environments

JAVA & MAVEN:

----------------------------------------------------------
	$ mvn -v
	Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-05T02:00:29+07:00)
	Maven home: /usr/local/Cellar/maven/3.6.1/libexec
	Java version: 11.0.4, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-11.0.4.jdk/Contents/Home
	Default locale: en_US, platform encoding: UTF-8
	OS name: "mac os x", version: "10.15.5", arch: "x86_64", family: "mac"
	
	NPM:
	$ npm -v
	6.14.4
	
	NODE:
	$ node -v
	v12.18.0

# Technologies that are used in this project:

	1. Spring security: setting up OAuth2 with OpenID Connect (OIDC) by Okta. (I don't use Facebook because I cannot grant ROLEs for users)
	2. Using JSON Web Token (JWT) for all APIs security.
	3. Using Spring Data framework & Hibernate & Liquidbase framework for repositories as RESTful resources, entities, & repositories. 
	4. Spring Boot & Spring Framework with embedded servlet container
	5. Support Internationalization (i18n)
	6. Apply Testing tools – Jest and Protractor
	7. Using OpenAPI specification (Swagger) for all microservices documentations.
	8. For simple demonstration I use H2 SQL databases.
	9. Using ReactJs for CMS-Admin webpage (ecommerce gateway project).
	10. Apply microservices pattern with: Registry, Gateway, Product Service, & Cart Service

# Build & run microservice project:

1. Run Registry (Eureka) and open localhost:8761 with proposed account
----------------------------------------------------------
	cd ${PROJECT_HOME}
	java -jar jhipster-registry-6.2.0.jar --spring.security.user.password=admin --jhipster.security.authentication.jwt.secret=ZDgyOWIxNmU1MGY3MDc4NTFjNzM0ZjM0OGI2YWY1MjJkZTM3Mjk1MDM4MmEyN2IyMzYxZDMyMWIyMzg0ZTYwZjBmYTQzOTg4MzE4ODk2M2EzOTg5YmVmYjAwMDZlZDU5NjRiNzBlY2ZjMGU0NjFlOWU0YWNjNjZjMDY2OTZlZTA= --spring.cloud.config.server.composite.0.type=native --spring.cloud.config.server.native.search.location=file:./central-config
----------------------------------------------------------

Output:

----------------------------------------------------------
	Application 'jhipster-registry' is running! Access URLs:
	Local: 		http://localhost:8761/
	External: 	http://127.0.0.1:8761/
	Profile(s): 	[composite, dev, swagger]
----------------------------------------------------------

Registry log checking:

	[ry-scheduling-2] i.g.j.r.service.ZuulUpdaterService       : Instance 'product:466e8776e7e19b6b97e5412e23e7c270' already registered
	[ry-scheduling-2] i.g.j.r.service.ZuulUpdaterService       : Instance 'gateway:b6b50b9231fb86b4687ac474265dce40' already registered
	[ry-scheduling-2] i.g.j.r.service.ZuulUpdaterService       : Instance 'cart:e8d162b849e0493be4042a397b445c1c' already registered

![Registry Status](https://github.com/dqvn/nabdemo/blob/master/imgs/Registry-status.png)

2. Run Product Service
----------------------------------------------------------
	cd ${PROJECT_HOME}/product
	./mvnw
----------------------------------------------------------

Output:
----------------------------------------------------------
	Application 'product' is running! Access URLs:
	Local: 		http://localhost:8081/
	External: 	http://127.0.0.1:8081/
	Profile(s): 	[dev, swagger]
----------------------------------------------------------

3. Run Cart Service
----------------------------------------------------------
	cd ${PROJECT_HOME}/cart
	./mvnw
----------------------------------------------------------

Output:
----------------------------------------------------------
	Application 'cart' is running! Access URLs:
	Local: 		http://localhost:8082/
	External: 	http://127.0.0.1:8082/
	Profile(s): 	[dev, swagger]
----------------------------------------------------------

4. Run Gateway (Zuul) and open localhost:8080 with proposed account
----------------------------------------------------------
	cd ${PROJECT_HOME}/gateway
	./mvnw
----------------------------------------------------------

Output:
----------------------------------------------------------
	Application 'gateway' is running! Access URLs:
	Local: 		http://localhost:8080/
	External: 	http://127.0.0.1:8080/
	Profile(s): 	[dev, swagger]
----------------------------------------------------------

# Login with Okta
![Registry Status](https://github.com/dqvn/nabdemo/blob/master/imgs/Gateway-CMS-login.png)

Check the Gateway Status
![Registry Status](https://github.com/dqvn/nabdemo/blob/master/imgs/Gateway-status.png)

# Demonstration Code:

----------------------------------------------------------
	Step 1 - Get code
   Open web browser by this url: 
   https://taptap-com.okta.com/oauth2/default/v1/authorize?client_id=0oafnw0apENilB3kG4x6&response_type=code&scope=openid&redirect_uri=http%3A%2F%2Flocalhost%3A9999%2Flogin%2Foauth2%2Fcode%2Foidc&state=state-296bc9a0-a2a2-4a57-be1a-d0e2fd9bb601

	-> redirect url after login: http://localhost:9999/login/oauth2/code/oidc?code=JTEVDUEB_3xAiup7xPFC&state=state-296bc9a0-a2a2-4a57-be1a-d0e2fd9bb601
----------------------------------------------------------

----------------------------------------------------------
	Step 2 - Get token (replace code=JTEVDUEB_3xAiup7xPFC in the curl)
	curl --request POST \
	--url https://taptap-com.okta.com/oauth2/default/v1/token \
	--header 'accept: application/json' \
	--header 'authorization: Basic MG9hZm53MGFwRU5pbEIza0c0eDY6djhFZHc4TjEwdi10b2drcVVQOTV0Z1RyaFMtNmw4eUU3T2pIV3pmMQ==' \
	--header 'content-type: application/x-www-form-urlencoded' \
	--data 'grant_type=authorization_code&redirect_uri=http%3A%2F%2Flocalhost%3A9999%2Flogin%2Foauth2%2Fcode%2Foidc&code=kFD2hYq_xWQigeZsoi0S' 

	output:
	{
	"token_type":"Bearer",
	"expires_in":3600,
	"access_token":"eyJraWQiOiJxZm9PT3Vyc3JVTl9rUDJxbXFLSFo5OTJ2amZ5WFNXZW94VlphVU1sdEFBIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULkNqQjBZaUNGWVZqWmRSTElQZ2ZlbnhZUFBiQ0RVeGdBdjVfT0thZlQteGsiLCJpc3MiOiJodHRwczovL3RhcHRhcC1jb20ub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTkyNTMyNjc2LCJleHAiOjE1OTI1MzYyNzYsImNpZCI6IjBvYWZudzBhcEVOaWxCM2tHNHg2IiwidWlkIjoiMDB1Zm53amc1bmNYbUJpOEY0eDYiLCJzY3AiOlsib3BlbmlkIl0sInN1YiI6ImFkbWluQGdtYWlsLmNvbSIsImdyb3VwcyI6WyJFdmVyeW9uZSIsIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXX0.ZLH_ghI0n9kvHFj2uvpwVWLVkF9IZhZuQJdplo2ZeiMmovMfshGVoYJ4FsegSahyAtvI0_arBSyz6Z27PBer4UTmoKWALzVJyvIFsJ8UzAprdy-JoUy6jqxInOw_1eDjN8lMl_h-3dXMbrB8v4OffAd97STyyWDz77x9U0EATdrjn9dUufhmwOSAUw4QF5KRqr6YcE4IGusq-8n5SACXHeBGDVzN4B1T4O6jUs1UCZPMg3ym4zhS8JiFRt8iOb9_MzlDqzQ5QXGGEZysJNAWBJpZcG4AqKKNg_bs3-Z9nDoV4DGHIr-037a-jcQdMxNw4Klkb2npKn0iIIiqzOGUMg",
	"scope":"openid",
	"id_token":"eyJraWQiOiJxZm9PT3Vyc3JVTl9rUDJxbXFLSFo5OTJ2amZ5WFNXZW94VlphVU1sdEFBIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiIwMHVmbndqZzVuY1htQmk4RjR4NiIsInZlciI6MSwiaXNzIjoiaHR0cHM6Ly90YXB0YXAtY29tLm9rdGEuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiMG9hZm53MGFwRU5pbEIza0c0eDYiLCJpYXQiOjE1OTI1MzI2NzYsImV4cCI6MTU5MjUzNjI3NiwianRpIjoiSUQuOEdZZ2RyNGxTWjZ5b3padEZzaUhPWm0xeEhrR0FpeDhsX2JnSTJTLXBsVSIsImFtciI6WyJwd2QiXSwiaWRwIjoiMDBvZmJxZGFtNldjem1xaHo0eDYiLCJhdXRoX3RpbWUiOjE1OTI1MzIwMjAsImF0X2hhc2giOiJnc0tjYW1ROTNva2ZTdzc3cmlMOThnIiwiZ3JvdXBzIjpbIkV2ZXJ5b25lIiwiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdfQ.fGUzlAg8H8FEaEpUKOB0Zrpy8Z_bKp-PwTaMZsasjLFF9w4LkuLnkXgrGLmxx6rjUSxwRvwWegIMVdSGAM-SXX57wdU6HyRtl1OPuuY7wXOhxxImzlObsmTS1Q3wWhHZlBwWKuDdj-8c-8vW4_PO7wat4vhLqQwzk1mn9YP-aXY35crkK9OFeqHmjPkMLHm1dzndV0Aim9zZN_slRnm1FKmLmDxi4URPjOrFf1jgAFFFvuLW5d1vNE5BNx8DO7_oUjrgreZ50_Ra6vob4EWHRYA0X8mmAxFaQLMLle8g8G0QeSRKOHNvXY5Dzn_BsFf4FG_rFxvwxhDJ4rJm5OyhnA"
	}
----------------------------------------------------------

----------------------------------------------------------
	Step 3 - Get all products by filter & paging by requirement
	curl -L -X GET 'http://localhost:8080/services/product/api/products?page=0&size=5&sort=productSize,desc&status.equals=CURRENT&brand.equals=JUNO' \
	-H 'Authorization: Bearer eyJraWQiOiJxZm9PT3Vyc3JVTl9rUDJxbXFLSFo5OTJ2amZ5WFNXZW94VlphVU1sdEFBIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULkNqQjBZaUNGWVZqWmRSTElQZ2ZlbnhZUFBiQ0RVeGdBdjVfT0thZlQteGsiLCJpc3MiOiJodHRwczovL3RhcHRhcC1jb20ub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTkyNTMyNjc2LCJleHAiOjE1OTI1MzYyNzYsImNpZCI6IjBvYWZudzBhcEVOaWxCM2tHNHg2IiwidWlkIjoiMDB1Zm53amc1bmNYbUJpOEY0eDYiLCJzY3AiOlsib3BlbmlkIl0sInN1YiI6ImFkbWluQGdtYWlsLmNvbSIsImdyb3VwcyI6WyJFdmVyeW9uZSIsIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXX0.ZLH_ghI0n9kvHFj2uvpwVWLVkF9IZhZuQJdplo2ZeiMmovMfshGVoYJ4FsegSahyAtvI0_arBSyz6Z27PBer4UTmoKWALzVJyvIFsJ8UzAprdy-JoUy6jqxInOw_1eDjN8lMl_h-3dXMbrB8v4OffAd97STyyWDz77x9U0EATdrjn9dUufhmwOSAUw4QF5KRqr6YcE4IGusq-8n5SACXHeBGDVzN4B1T4O6jUs1UCZPMg3ym4zhS8JiFRt8iOb9_MzlDqzQ5QXGGEZysJNAWBJpZcG4AqKKNg_bs3-Z9nDoV4DGHIr-037a-jcQdMxNw4Klkb2npKn0iIIiqzOGUMg'

	output:
	[ {
	  "createdBy" : "system",
	  "createdDate" : "2020-06-18T15:23:38.754437Z",
	  "lastModifiedBy" : null,
	 "lastModifiedDate" : null,
	  "id" : 3,
	  "name" : "Internal compressing",
	  "brand" : "JUNO",
	  "price" : 60561.00,
	  "productColor" : "GREEN",
	  "productSize" : "XXL",
	  "status" : "CURRENT",
	  "productCategory" : null
	}, {
	  "createdBy" : "system",
	  "createdDate" : "2020-06-18T15:23:38.754437Z",
	  "lastModifiedBy" : null,
	  "lastModifiedDate" : null,
	  "id" : 9,
	  "name" : "interactive",
	  "brand" : "JUNO",
	  "price" : 44886.00,
	  "productColor" : "GREY",
	  "productSize" : "XL",
	  "status" : "CURRENT",
	  "productCategory" : null
	}, {
	  "createdBy" : "system",
	  "createdDate" : "2020-06-18T08:30:12.447861Z",
	  "lastModifiedBy" : "system",
	  "lastModifiedDate" : "2020-06-18T08:34:37.180244Z",
	  "id" : 11,
	  "name" : "sdfasf",
	  "brand" : "JUNO",
	  "price" : 100000.00,
	  "productColor" : "RED",
	  "productSize" : "S",
	  "status" : "CURRENT",
	  "productCategory" : {
	    "createdBy" : "system",
	    "createdDate" : "2020-06-18T15:23:38.783998Z",
	    "lastModifiedBy" : null,
	    "lastModifiedDate" : null,
	    "id" : 1,
	    "name" : "firmware",
	    "description" : "Liaison compress proactive"
	  }
	} ]
----------------------------------------------------------

----------------------------------------------------------
	Step 4: When customer add new product to cart -> create new Order as below with code ORDER002
	curl -L -X POST 'http://localhost:8080/services/cart/api/product-orders' \
	-H 'Authorization: Bearer eyJraWQiOiJxZm9PT3Vyc3JVTl9rUDJxbXFLSFo5OTJ2amZ5WFNXZW94VlphVU1sdEFBIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULm1NZWJzV3N5U29JSFF5M2twVklxT2RuSHA2SWo1RWtuRlRkellTY2Nqa1kiLCJpc3MiOiJodHRwczovL3RhcHRhcC1jb20ub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTkyNTM1MDkwLCJleHAiOjE1OTI1Mzg2OTAsImNpZCI6IjBvYWZudzBhcEVOaWxCM2tHNHg2IiwidWlkIjoiMDB1Zm53amc1bmNYbUJpOEY0eDYiLCJzY3AiOlsib3BlbmlkIl0sInN1YiI6ImFkbWluQGdtYWlsLmNvbSIsImdyb3VwcyI6WyJFdmVyeW9uZSIsIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXX0.KFNazww42k0UUsdoOV7TI1Wu9z0o4WNr9W6SF4fcGE0f-uUt5VEmLreUDAIq46NLISBGYLX5vhUqk661cDEIachUcmp040C7tBC7YNPAjf0Sy8yAihB0QkONCHd3zIfHS8wH27wnu7gWqlLgHiw7nkX1mYVacEnP0qMGZJMGGdYOZrp1J-tHwJVuFm5AlzQD4L7Sn8693Q6ztYqmAXtRHq8cOpMY7G1Prli5XPPcVrfLQI39R0eZyhllhIF6aU-s5AZITwM3VMNTQ4gTnGScZUgoStrkwki5DAlLFhwdjwStcQzQZLBNybYKu5ZrDFfvVd0ispKREHeIf3sv6DLrQA' \
	-H 'Content-Type: application/json;charset=UTF-8' \
	--data-raw '{"placedDate":"2020-06-18T17:00:00.000Z","status":"PENDING","code":"ORDER002","customer":"admin"}'

	response
	{
	  "createdBy" : "system",
	  "createdDate" : "2020-06-19T02:53:02.293691Z",
	  "lastModifiedBy" : "system",
	  "lastModifiedDate" : "2020-06-19T02:53:02.293691Z",
	  "id" : 13,
	  "placedDate" : "2020-06-18T17:00:00Z",
	  "status" : "PENDING",
	  "code" : "ORDER002",
	  "customer" : "admin",
	  "orderItems" : [ ]
	}


----------------------------------------------------------
	Step 5: add Items to the cart order
	curl -L -X POST 'http://localhost:8080/services/cart/api/order-items' \
	-H 'Authorization: Bearer eyJraWQiOiJxZm9PT3Vyc3JVTl9rUDJxbXFLSFo5OTJ2amZ5WFNXZW94VlphVU1sdEFBIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULm1NZWJzV3N5U29JSFF5M2twVklxT2RuSHA2SWo1RWtuRlRkellTY2Nqa1kiLCJpc3MiOiJodHRwczovL3RhcHRhcC1jb20ub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTkyNTM1MDkwLCJleHAiOjE1OTI1Mzg2OTAsImNpZCI6IjBvYWZudzBhcEVOaWxCM2tHNHg2IiwidWlkIjoiMDB1Zm53amc1bmNYbUJpOEY0eDYiLCJzY3AiOlsib3BlbmlkIl0sInN1YiI6ImFkbWluQGdtYWlsLmNvbSIsImdyb3VwcyI6WyJFdmVyeW9uZSIsIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXX0.KFNazww42k0UUsdoOV7TI1Wu9z0o4WNr9W6SF4fcGE0f-uUt5VEmLreUDAIq46NLISBGYLX5vhUqk661cDEIachUcmp040C7tBC7YNPAjf0Sy8yAihB0QkONCHd3zIfHS8wH27wnu7gWqlLgHiw7nkX1mYVacEnP0qMGZJMGGdYOZrp1J-tHwJVuFm5AlzQD4L7Sn8693Q6ztYqmAXtRHq8cOpMY7G1Prli5XPPcVrfLQI39R0eZyhllhIF6aU-s5AZITwM3VMNTQ4gTnGScZUgoStrkwki5DAlLFhwdjwStcQzQZLBNybYKu5ZrDFfvVd0ispKREHeIf3sv6DLrQA' \
	-H 'Content-Type: application/json;charset=UTF-8' \
	--data-raw '{"quantity":"5","totalPrice":"0","status":"AVAILABLE","productName":"Thoi trang nguoi lon","order":{"id":"13"}}'
	
	response
	{
	  "createdBy" : "system",
	  "createdDate" : "2020-06-19T02:59:26.905959Z",
	  "lastModifiedBy" : "system",
	  "lastModifiedDate" : "2020-06-19T02:59:26.905959Z",
	  "id" : 11,
	  "quantity" : 5,
	  "totalPrice" : 50000,
	  "status" : "AVAILABLE",
	  "productName" : "Thoi trang tre em",
	  "order" : {
	    "createdBy" : null,
	    "createdDate" : "2020-06-19T02:59:26.885877Z",
	    "lastModifiedBy" : null,
	    "lastModifiedDate" : "2020-06-19T02:59:26.885878Z",
	    "id" : 13,
	    "placedDate" : null,
	    "status" : null,
	    "code" : null,
	    "customer" : null
	  }
	}
----------------------------------------------------------

----------------------------------------------------------
	Step 6: after all item adding to cart in step 5, user press order button and confirm then the api update order status to COMPLETED
	curl -L -X PUT 'http://localhost:8080/services/cart/api/product-orders' \
	-H 'Authorization: Bearer eyJraWQiOiJxZm9PT3Vyc3JVTl9rUDJxbXFLSFo5OTJ2amZ5WFNXZW94VlphVU1sdEFBIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULjNSLS1hc1hJbHYxNVg5dWM2QW9kTVZoLVA5WFRnZVlJT3hZdDN3WjlMOWsiLCJpc3MiOiJodHRwczovL3RhcHRhcC1jb20ub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTkyNTQ2NzcwLCJleHAiOjE1OTI1NTAzNzAsImNpZCI6IjBvYWZudzBhcEVOaWxCM2tHNHg2IiwidWlkIjoiMDB1Zm53amc1bmNYbUJpOEY0eDYiLCJzY3AiOlsib3BlbmlkIl0sInN1YiI6ImFkbWluQGdtYWlsLmNvbSIsImdyb3VwcyI6WyJFdmVyeW9uZSIsIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXX0.IEPhmXuG_jwScyS50rGpGX_8fHjbOTI82CCkiDfiRRbzxCRLCfvAcECcjNooajXvCt5uq7mE1q36a9DBP-5o_6jeJSFYs5h21SMD_KU-j6WFLyMtD1V2anWKcnLLRo9QTp3a4vf2NL4qak3fRGCT3jWUL0ArXj66BtQO_pedAvjBEXVxIZ2hGp4GgdidmpOtV79-mFGW4AmKAeHqQ-RWzxflph93bE9dCn3REsYrhqh2Zxng9YlcRR8CBCJMnfAFr-2OAux2_BLYlZWeFCZLM63LeiNxKOV5_ylLQFxxVZxKxohcAdpY3c8RpnUh9pBhmP3ZdC253eET1WLb2ldXeQ' \
	-H 'Content-Type: application/json;charset=UTF-8' \
	--data-raw '{"id":13,"status":"COMPLETED","customer": "admin","placedDate": "2020-06-19T02:59:26.905959Z","code": "ORDER003","createdBy" : "system"}'
	
	response
	{
	  "createdBy" : "system",
	  "createdDate" : "2020-06-19T02:53:02.293691Z",
	  "lastModifiedBy" : "system",
	  "lastModifiedDate" : "2020-06-19T03:08:11.042508Z",
	  "id" : 13,
	  "placedDate" : "2020-06-18T17:00:00Z",
	  "status" : "COMPLETED",
	  "code" : "ORDER002",
	  "customer" : "admin",
	  "orderItems" : null
	}
----------------------------------------------------------

# Audits in Database log

By default, all login log will be store in db as below

![Login Audits](https://github.com/dqvn/nabdemo/blob/master/imgs/AuditLogin.png)

For audit support, all customers' activities such as searching, filtering and viewing product's details need to be stored in the database. However, failure to store customer activity is completely transparent to customer and should has no impact to the activity itself.

	Step 1: login to http://localhost:8081/h2-console/login.do?jsessionid=967bb2321a3031ff9f6015288cf3d243
	Step 2: login with sa account (no password)
	Step 3: query "SELECT * FROM JHI_ENTITY_AUDIT_EVENT order by id desc;"

This will show all Entity Audit log (VIEW, UPDATE, CREATE, DELETE events)

![Product Entity Activity Audits](https://github.com/dqvn/nabdemo/blob/master/imgs/EntityAuditLog.png)

To help me do this, I wrote a class (EntityAuditEventListener.java) based on PostLoad, PostCreate, PostUpdate events from java.persistence.* class
By using try-catch, it will not affect to user behavior while using the application whenever the log events is fail or broken.

![EntityAuditEventListener.java](https://github.com/dqvn/nabdemo/blob/master/imgs/EntityAuditEventListener.png)

# Filter, short and search for products based on different criteria such as name, price, brand, colour etc.

From the API document (Swagger), I could do search, filter, sorting based on any kind of criteria what i need as below
![product filtering](https://github.com/dqvn/nabdemo/blob/master/imgs/api-product-filter.png)

As a result, you will have a curl call as below:

	curl -X GET "http://localhost:8080/services/product/api/products?brand.equals=NOUS&brand.notEquals=KAFPA&id.lessThan=50&id.notEquals=20&price.greaterThan=300000&productColor.in=YELLOW&productColor.notIn=RED&productSize.notIn=M&status.in=CURRENT&page=1&size=10&sort=productSize%2Casc"

# All product prices are subject to change at any time and the company wants to keep track of it.

For this rule, I do optimize the Update event whenever the CURRENT product price will be updated.
I am going to check whether the Current Product Price change, then I will clone this product to a new object with new price and keep the current object with old price & change to OUTDATE status

	    /**
	     * {@code PUT  /products} : Updates an existing product.
	     *
	     * @param product the product to update.
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated product,
	     * or with status {@code 400 (Bad Request)} if the product is not valid,
	     * or with status {@code 500 (Internal Server Error)} if the product couldn't be updated.
	     * @throws URISyntaxException if the Location URI syntax is incorrect.
	     */
	    @PutMapping("/products")
	    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) throws URISyntaxException {
	        log.debug("REST request to update Product : {}", product);
	        if (product.getId() == null) {
	            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
	        }
	        
	        Product result = null;
	        // check if product price change, then create new object with new price and keep the current object with old price with OUTDATE status
	        Product currentProduct = productService.findOne(product.getId()).get();
	        if (currentProduct != null && product.getPrice() != null && currentProduct.getPrice() != null 
	                && ProductStatus.CURRENT.compareTo(product.getStatus()) == 0 && currentProduct.getPrice().compareTo(product.getPrice()) != 0) {
	            product.setId(null); // create new record
	            product.setStatus(ProductStatus.CURRENT);
	            result = productService.save(product);
	
	            // outdate current product
	            currentProduct.setStatus(ProductStatus.OUTDATE);
	            productService.save(currentProduct);
	        } else {
	            result = productService.save(product);
	        }
	
	        return ResponseEntity.ok()
	            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, product.getId().toString()))
	            .body(result);
	    }

After many product update, the results is below:
![Product Price Update Rule](https://github.com/dqvn/nabdemo/blob/master/imgs/productPrizeUpdateRule.png)

And the DB Entity log as below:

![Product Price Update Log](https://github.com/dqvn/nabdemo/blob/master/imgs/productPrizeUpdateLog.png)


# Demonstrate inter-service communication between these services

To help demo the inter-service communication between product service & cart service, I choose the user case that after user choose the product and he/she wants to add to cart: the cart service must call to product service to check if product is existed or not. And in case existed, it will get the price from product service and multiply with quantity of this product in cart -> then cart service will update the final (total price) number for this item as this code below:

Firstly, I have to registry the FeignClient an API call to "product" service and indicate correct /api/products API from product service to get data

	import org.springframework.web.bind.annotation.RequestMapping;
	
	import com.nab.cart.client.AuthorizedFeignClient;
	import com.nab.cart.service.dto.Product;
	
	@AuthorizedFeignClient(name = "product")
	interface ProductServiceClient {
	
	  @RequestMapping(value = "/api/products")
	  Product[] getProductFromProductService();
	  
	}


After that, from any Cart services/controllers, I could do inter-service call between these services:

	     * {@code POST  /order-items} : Create a new orderItem.
	     *
	     * @param orderItem the orderItem to create.
	     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderItem, or with status {@code 400 (Bad Request)} if the orderItem has already an ID.
	     * @throws URISyntaxException if the Location URI syntax is incorrect.
	     */
	    @PostMapping("/order-items")
	    public ResponseEntity<OrderItem> createOrderItem(@Valid @RequestBody OrderItem orderItem) throws URISyntaxException {
	        log.debug("REST request to save OrderItem : {}", orderItem);
	        if (orderItem.getId() != null) {
	            throw new BadRequestAlertException("A new orderItem cannot already have an ID", ENTITY_NAME, "idexists");
	        }
	        
	        // calling other product service to get all products and do filtering
	        Product[] product = productServiceClient.getProductFromProductService();
	        for (Product p : product) {
	        	if (p.getName().equalsIgnoreCase(orderItem.getProductName().trim()) && p.getStatus().equalsIgnoreCase("CURRENT")) {
	        		log.info("Found product from Product service: " + p.toString());
	        		orderItem.setTotalPrice(p.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
	        		log.info("#### Total price of this order: " + orderItem.getTotalPrice());
	        	}
	        }
	        
	        OrderItem result = orderItemService.save(orderItem);
	        return ResponseEntity.created(new URI("/api/order-items/" + result.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
	            .body(result);
	    }

