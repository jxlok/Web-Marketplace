CREATE DATABASE pawn;
USE pawn;

CREATE TABLE test_table
(
    id   INTEGER AUTO_INCREMENT,
    name TEXT,
    PRIMARY KEY (id)
) COMMENT='this is my test table';


CREATE TABLE orders
(
    orderID         INTEGER AUTO_INCREMENT,
    orderPlacedTime TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    orderStatus     varchar(255) NOT NULL,
    paymentID       INTEGER,
    customerID      INTEGER      NOT NULL,
    PRIMARY KEY (orderID)
);

CREATE TABLE order_details
(
    id        INTEGER AUTO_INCREMENT,
    orderID   INTEGER NOT NULL,
    itemID    INTEGER NOT NULL,
    quantity  INTEGER NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (orderID) REFERENCES orders (orderID)
);

CREATE TABLE customers
(
    customerID INTEGER AUTO_INCREMENT,
    email      varchar(50) NOT NULL,
    password   char(60)    NOT NULL,
    UNIQUE (email),
    PRIMARY KEY (customerID)
);

CREATE TABLE admins
(
    adminId  INTEGER AUTO_INCREMENT,
    email    varchar(50) NOT NULL,
    password char(60)    NOT NULL,
    UNIQUE (email),
    PRIMARY KEY (adminId)
);

CREATE TABLE items
(
    itemId         INTEGER AUTO_INCREMENT,
    itemName       varchar(255)   NOT NULL,
    description    varchar(255)   NOT NULL,
    isTrained      TINYINT        NOT NULL,
    price          DECIMAL(10, 2) NOT NULL,
    stock          INTEGER        NOT NULL,
    visibility     TINYINT        NOT NULL,

    PRIMARY KEY (itemId)
);

CREATE TABLE carts
(
    id         INTEGER AUTO_INCREMENT,
    customerID INTEGER NOT NULL,
    itemID     INTEGER NOT NULL,
    isTrained  TINYINT NOT NULL,
    quantity   INTEGER NOT NULL,

    UNIQUE KEY `customerID_itemID_isTrained`(`customerID`,`itemID`,`isTrained`),
    PRIMARY KEY (id),
    FOREIGN KEY (customerID) REFERENCES customers (customerID),
    FOREIGN KEY (itemID) REFERENCES items (itemID)
);

INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("item1", "description1", 1, 100.00, 10, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("item2", "description2", 2, 200.00, 10, 1);
INSERT INTO orders (orderStatus, paymentID, customerID) VALUES ("New", 2, 200);
INSERT INTO orders (orderStatus, paymentID, customerID) VALUES ("New", 1, 100);
INSERT INTO order_details (orderID, itemID, quantity) VALUES (1, 1, 2);
INSERT INTO order_details (orderID, itemID, quantity) VALUES (1, 2, 1);
INSERT INTO order_details (orderID, itemID, quantity) VALUES (2, 2, 2);

INSERT INTO admins (email, password) VALUES ("admin@admin.ie", "password")
