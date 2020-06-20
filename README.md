# nabdemo
This is a project for nabdemo

# Design project
Read requirements and do design compose on JDL-Studio
![Read requirements and do design compose on JDL-Studio](https://github.com/dqvn/nabdemo/blob/master/imgs/ERD_final.png)

This is the full entities design:
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


