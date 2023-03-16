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
    paymentID       INTEGER      NOT NULL,
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
    password   varchar(64) NOT NULL,
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
    description    varchar(2000)   NOT NULL,
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



INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Trend Micro Cloud One", "[trained] NEW! Trend Micro Cloud Sentry™ - Giving you a new perspective on securing business critical applications. Cloud Sentry identifies threats in minutes with no performance impact, all while keeping your data in your environment. You will get context rich-insights into risks, allowing you to effectively prioritize and implement mitigations. Plus, with Cloud Sentry, you can see all resources and security findings by AWS account using the Cloud One Central dashboard and remediate with just one click. Click subscribe to start your free trial! Remember, your 30-day free trial includes access to the entire Trend Micro Cloud One platform + access to our always free tiers. To learn more about our always-free tiers check out the additional support section!", 1, 100.00, 10, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Trend Micro Cloud One", "[untrained] NEW! Trend Micro Cloud Sentry™ - Giving you a new perspective on securing business critical applications. Cloud Sentry identifies threats in minutes with no performance impact, all while keeping your data in your environment. You will get context rich-insights into risks, allowing you to effectively prioritize and implement mitigations. Plus, with Cloud Sentry, you can see all resources and security findings by AWS account using the Cloud One Central dashboard and remediate with just one click. Click subscribe to start your free trial! Remember, your 30-day free trial includes access to the entire Trend Micro Cloud One platform + access to our always free tiers. To learn more about our always-free tiers check out the additional support section!", 0, 40.00, 10, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Kali Linux", "[trained] Kali Linux (formerly known as BackTrack Linux) is an open-source, Debian-based Linux distribution aimed at advanced Penetration Testing and Security Auditing. It does this by providing common tools, configurations, and automations which allows the user to focus on the task that needs to be completed, not the surrounding activity.Kali Linux contains industry specific modifications as well as several hundred tools targeted towards various Information Security tasks, such as Penetration Testing, Security Research, Computer Forensics, Reverse Engineering, Vulnerability Management and Red Team Testing.Kali Linux is a multi-platform solution, accessible and freely available to information security professionals and hobbyists.", 1, 200.00, 10, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Kali Linux", "[untrained] Kali Linux (formerly known as BackTrack Linux) is an open-source, Debian-based Linux distribution aimed at advanced Penetration Testing and Security Auditing. It does this by providing common tools, configurations, and automations which allows the user to focus on the task that needs to be completed, not the surrounding activity.Kali Linux contains industry specific modifications as well as several hundred tools targeted towards various Information Security tasks, such as Penetration Testing, Security Research, Computer Forensics, Reverse Engineering, Vulnerability Management and Red Team Testing.Kali Linux is a multi-platform solution, accessible and freely available to information security professionals and hobbyists.", 0, 90.00, 10, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Fortinet FortiGate Next-Generation Firewall", "[trained] FortiGate-VM on AWS delivers next-generation firewall and VPN/SD-WAN capabilities for organizations of all sizes. It enables broad network protection and automated security management for consistent enforcement and visibility across your AWS VPCs and hybrid cloud infrastructure. FortiGate natively integrates with AWS Gateway Load Balancer, AWS Transit Gateway and other AWS security services to simplify and deliver enterprise-class security for applications and workloads running on AWS.FortiGate-VM reduces complexity by combining secure connectivity with advanced threat protection capabilities such as powerful intrusion prevention (IPS), malware detection and protection, and continuous threat intelligence from FortiGuard Labs security services. It offers a management console that provides comprehensive network automation and unified visibility across multi-cloud environments.FortiGate-VM, in concert with other elements of the Fortinet Security Fabric, enables common deployment scenarios such as cloud security services hub, secure remote access, container security, web application security, and critical workload protection", 1, 150.00, 10, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Fortinet FortiGate Next-Generation Firewall", "[untrained] FortiGate-VM on AWS delivers next-generation firewall and VPN/SD-WAN capabilities for organizations of all sizes. It enables broad network protection and automated security management for consistent enforcement and visibility across your AWS VPCs and hybrid cloud infrastructure. FortiGate natively integrates with AWS Gateway Load Balancer, AWS Transit Gateway and other AWS security services to simplify and deliver enterprise-class security for applications and workloads running on AWS.FortiGate-VM reduces complexity by combining secure connectivity with advanced threat protection capabilities such as powerful intrusion prevention (IPS), malware detection and protection, and continuous threat intelligence from FortiGuard Labs security services. It offers a management console that provides comprehensive network automation and unified visibility across multi-cloud environments.FortiGate-VM, in concert with other elements of the Fortinet Security Fabric, enables common deployment scenarios such as cloud security services hub, secure remote access, container security, web application security, and critical workload protection", 0, 70.00, 10, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Debian 10 Buster", "[trained] Debian is a free operating system, developed by thousands of volunteers from all over the world who collaborate via the Internet. The Debian project''s key strengths are its volunteer base, its dedication to the Debian Social Contract and Free Software, and its commitment to provide the best operating system possible. This new release is another important step in that direction.", 1, 200.00, 10, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Debian 10 Buster", "[untrained] Debian is a free operating system, developed by thousands of volunteers from all over the world who collaborate via the Internet. The Debian project''s key strengths are its volunteer base, its dedication to the Debian Social Contract and Free Software, and its commitment to provide the best operating system possible. This new release is another important step in that direction.", 0, 120.00, 10, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Ubuntu 18.04 LTS - Bionic", "[trained] Lean, fast and powerful, Ubuntu Server delivers services reliably, predictably and economically. It is the perfect base on which to build your instances. Ubuntu is free and will always be, and you have the option to get support and Landscape from Canonical.", 1, 80.00, 15, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Ubuntu 18.04 LTS - Bionic", "[untrained] Lean, fast and powerful, Ubuntu Server delivers services reliably, predictably and economically. It is the perfect base on which to build your instances. Ubuntu is free and will always be, and you have the option to get support and Landscape from Canonical.", 0, 35.00, 15, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Splunk Enterprise", "[trained] The Splunk Enterprise AMI accelerates the speed at which organizations deploy Splunk Enterprise in AWS. Splunk Enterprise is the leading platform for Operational Intelligence, delivering an easy, fast, and secure way to search, analyze and visualize the massive streams of machine data generated by your IT systems and technology infrastructure - physical, virtual and in the cloud. Use this AMI to take Splunk for a test drive, or as the basis for your Enterprise-level deployment. The Splunk Enterprise AMI ships with a fully-featured trial license that is valid for 60 days after launch. After the trial expires, your deployment will default to Splunk Free.", 1, 300.00, 15, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Splunk Enterprise", "[untrained] The Splunk Enterprise AMI accelerates the speed at which organizations deploy Splunk Enterprise in AWS. Splunk Enterprise is the leading platform for Operational Intelligence, delivering an easy, fast, and secure way to search, analyze and visualize the massive streams of machine data generated by your IT systems and technology infrastructure - physical, virtual and in the cloud. Use this AMI to take Splunk for a test drive, or as the basis for your Enterprise-level deployment. The Splunk Enterprise AMI ships with a fully-featured trial license that is valid for 60 days after launch. After the trial expires, your deployment will default to Splunk Free.", 0, 160.00, 15, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Palo Alto Networks Panorama", "[trained] Panorama network security management enables you to control your distributed network of our firewalls from one central location. View all your firewall traffic, manage all aspects of device configuration, push global policies, and generate reports on traffic patterns or security incidents - all from a single console.", 1, 400.00, 15, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Palo Alto Networks Panorama", "[untrained] Panorama network security management enables you to control your distributed network of our firewalls from one central location. View all your firewall traffic, manage all aspects of device configuration, push global policies, and generate reports on traffic patterns or security incidents - all from a single console.", 0, 210.00, 15, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("MongoDB Atlas (Pay as You Go)", "[trained] Run fully managed MongoDB on AWS with MongoDB Atlas, a global application data platform that gives you the versatility you need to build a wide variety of applications that can rapidly adapt to evolving customer and market demands. Power transactional processing, relevance based search, real-time analytics, and more through an elegant and integrated data architecture. Atlas (Mongo as a Service) provides a first class developer experience through a flexible document data model and unified query interface while meeting the most demanding requirements for resilience, scale, and data privacy.MongoDB Atlas is available in more than 20 AWS public cloud regions. Get started with MongoDB Atlas with 512 MB of storage for $0 with the free trial tier. Dedicated clusters start at USD 0.08 per hour, with the ability to scale up and out as needed. Costs vary according to cluster configurations, network usage, backup policies, and use of additional features.We are leveraging AWS Standard Contract for MP (SCMP) as the EULA", 1, 90.00, 15, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("MongoDB Atlas (Pay as You Go)", "[untrained] Run fully managed MongoDB on AWS with MongoDB Atlas, a global application data platform that gives you the versatility you need to build a wide variety of applications that can rapidly adapt to evolving customer and market demands. Power transactional processing, relevance based search, real-time analytics, and more through an elegant and integrated data architecture. Atlas (Mongo as a Service) provides a first class developer experience through a flexible document data model and unified query interface while meeting the most demanding requirements for resilience, scale, and data privacy.MongoDB Atlas is available in more than 20 AWS public cloud regions. Get started with MongoDB Atlas with 512 MB of storage for $0 with the free trial tier. Dedicated clusters start at USD 0.08 per hour, with the ability to scale up and out as needed. Costs vary according to cluster configurations, network usage, backup policies, and use of additional features.We are leveraging AWS Standard Contract for MP (SCMP) as the EULA", 0, 45.00, 15, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("OpenVPN Access Server", "[trained] OpenVPN Access Server, the OpenVPN self-hosted solution, simplifies the rapid deployment of a secure remote access solution with a web-based graphic user interface and OpenVPN Connect client installers. We also offer OpenVPN Cloud for small, medium, and enterprise businesses that prefer full cloud networking.Get Technical Support 24/7 and Schedule a Live Demo at openvpn.com.Our products are based on the market-proven OpenVPN protocol and trusted by some of the world''s most renowned brands for their unmatched flexibility, scalability, and ease of use.OpenVPN Access Server delivers the enterprise VPN your business has been looking for. Protect your data communications, secure IoT resources, and provide encrypted remote access to on-premise, hybrid, and public cloud resources.Access Server provides you with a powerful and easy-to-use web-based admin site that makes VPN management and configuration simple for anybody (with or without Linux knowledge). Access Server integrates OpenVPN server capabilities, access management, and OpenVPN client software that accommodates Windows, macOS, Linux, Android, iOS, and ChromeOS environments.Our licensing model is based on the number of concurrent connected devices, so it''s affordable for any size business and can easily grow with your company. Without a license key installed, OpenVPN Access Server will allow 2 concurrent connections at no additional cost (excepting AWS infrastructure costs).", 1, 165.00, 1, 1);
INSERT INTO items (itemName, description, isTrained, price, stock, visibility) VALUES ("Cisco Meraki vMX", "[untrained] As part of Cisco''s Cloud connect portfolio, Meraki''s virtual MX extends your physical MX deployment in minutes through the same Meraki dashboard. vMX can be used as your SD-WAN and Auto VPN node to easily connect your network with your AWS deployed services. Leveraging the power of the cloud, Cisco Meraki''s virtual MX can configure, monitor, and maintain your VPN so you don''t have to. ", 0, 140.00, 15, 1);





INSERT INTO orders (orderStatus, paymentID, customerID) VALUES ("New", 2, 200);
INSERT INTO orders (orderStatus, paymentID, customerID) VALUES ("New", 1, 100);
INSERT INTO order_details (orderID, itemID, quantity) VALUES (1, 1, 2);
INSERT INTO order_details (orderID, itemID, quantity) VALUES (1, 2, 1);
INSERT INTO order_details (orderID, itemID, quantity) VALUES (2, 2, 2);
-- customer password: secret, by sha3-256
INSERT INTO customers (customerID, email, password) VALUES (111, '111@gmail.com', 'f5a5207a8729b1f709cb710311751eb2fc8acad5a1fb8ac991b736e69b6529a3');
INSERT INTO carts (customerID, itemID, isTrained, quantity) VALUES (111, 1, 1, 1);
INSERT INTO carts (customerID, itemID, isTrained, quantity) VALUES (111, 3, 0, 2);
INSERT INTO carts (customerID, itemID, isTrained, quantity) VALUES (111, 2, 0, 3);

