create database ranweliTours;

use ranweliTours;

create table admin(
                      email varchar(255) primary key,
                      userName varchar(15) not null,
                      password varchar(30) not null,
                      type varchar (15) not null
);


create table employee(
                         empId varchar(30) primary key,
                         name varchar(25) not null,
                         address varchar(100)not null,
                         type ENUM('DRIVER','GUIDE','OTHER'),
                         availability ENUM('YES','NO'),
                         salary DECIMAL(10,2),
                         admin varchar(255),
                         FOREIGN KEY(admin)REFERENCES admin(email) ON DELETE CASCADE ON UPDATE CASCADE
);

create table vehicle(
                        vehicleId varchar(30) primary key,
                        status ENUM('YES','NO'),
                        numberOfSeats int(5),
                        empId varchar(30),
                        FOREIGN KEY(empId)REFERENCES employee(empId) ON DELETE CASCADE ON UPDATE CASCADE
);

create table tourist(
                        identityDetails varchar(100) primary key,
                        name varchar(25) not null,
                        password varchar(35) not null
);

create table payment(
                        payId varchar(30) primary key,
                        amount DECIMAL (10,2) not null,
                        status ENUM('PAID','NOTPAID'),
                        paidDate date,
                        method ENUM('CASH','CARD'),
                        receipt LONGBLOB
);

create table hotel(
                      hotelId varchar(30) primary key,
                      name varchar (30) not null,
                      type varchar(70) ,
                      status ENUM('YES','NO')

);

create table package(
                        packageId varchar(30) primary key,
                        name varchar(30) not null,
                        description varchar (255),
                        price DECIMAL(10,2) not null
);

create table hotel_package_details(
                                      hotelId varchar(30),
                                      packageId varchar(30),
                                      bookedDate date,
                                      primary key (hotelId,packageId),
                                      FOREIGN KEY(hotelId)REFERENCES hotel(hotelId)ON DELETE CASCADE ON UPDATE CASCADE,
                                      FOREIGN KEY(packageId)REFERENCES package(packageId)ON DELETE CASCADE ON UPDATE CASCADE
);

create table vehicle_package_details(
                                        vehicleId varchar(30),
                                        packageId varchar(30),
                                        primary key (vehicleId,packageId),
                                        FOREIGN KEY(vehicleId)REFERENCES vehicle(vehicleId)ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY(packageId)REFERENCES package(packageId)ON DELETE CASCADE ON UPDATE CASCADE
);

create table booking(
                        bookingId varchar(30) primary key,
                        packageId varchar(30),
                        touristId varchar(30),
                        payId varchar(30),
                        bookingDate date,
                        FOREIGN KEY(packageId)REFERENCES package(packageId)ON DELETE CASCADE ON UPDATE CASCADE,
                        FOREIGN KEY(touristId)REFERENCES tourist(identityDetails)ON DELETE CASCADE ON UPDATE CASCADE,
                        FOREIGN KEY(payId)REFERENCES payment(payId)ON DELETE CASCADE ON UPDATE CASCADE
);