# nabdemo
This is a project for nabdemo

# Design project
Read requirements and do design & sperate components based on JDL-Studio
![Read requirements and do design compose on JDL-Studio](https://github.com/dqvn/nabdemo/blob/master/imgs/ERD_final.png)

# Modeling microservice entities in JDL: This is the full entities:
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
By my familiar with Microservices stacks and the freestyle requirement to use framework, thus I used the JH for quicly create project as image

![By my familiar with Microservices stacks and the freestyle requirement to use framework, thus I used the JH for quicly create project as image](https://github.com/dqvn/nabdemo/blob/master/imgs/JH-Project-structure.png)

# Project Build environments
JAVA & MAVEN:
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

# 
