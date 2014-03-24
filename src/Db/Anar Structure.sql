/*
SQLyog Ultimate v8.3 
MySQL - 5.5.18 : Database - anar
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`anar` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `anar`;

/*Table structure for table `sample` */

DROP TABLE IF EXISTS `sample`;

CREATE TABLE `sample` (
  `sampleId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`sampleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_advancepayment` */

DROP TABLE IF EXISTS `tbl_advancepayment`;

CREATE TABLE `tbl_advancepayment` (
  `advancePaymentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `empId` bigint(20) DEFAULT NULL,
  `empName` varchar(100) DEFAULT NULL,
  `pMonth` varchar(50) DEFAULT NULL,
  `pYear` varchar(20) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `paidBy` varchar(100) DEFAULT NULL,
  `paidDate` date DEFAULT NULL,
  PRIMARY KEY (`advancePaymentId`),
  KEY `FK_tbl_advancepayment` (`empId`),
  CONSTRAINT `FK_tbl_advancepayment` FOREIGN KEY (`empId`) REFERENCES `tbl_labourdetails` (`empId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_conemployee` */

DROP TABLE IF EXISTS `tbl_conemployee`;

CREATE TABLE `tbl_conemployee` (
  `empId` bigint(20) NOT NULL AUTO_INCREMENT,
  `empName` varchar(100) DEFAULT NULL,
  `joinDate` date DEFAULT NULL,
  `nationality` varchar(100) DEFAULT NULL,
  `profession` varchar(100) DEFAULT NULL,
  `passportNumber` varchar(100) DEFAULT NULL,
  `idNumber` varchar(100) DEFAULT NULL,
  `passportExpiry` date DEFAULT NULL,
  `idExpiry` date DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `site` varchar(100) DEFAULT NULL,
  `parentCompany` varchar(100) DEFAULT NULL,
  `contractingCompany` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_currentsite` */

DROP TABLE IF EXISTS `tbl_currentsite`;

CREATE TABLE `tbl_currentsite` (
  `siteId` bigint(20) NOT NULL AUTO_INCREMENT,
  `empId` bigint(20) DEFAULT NULL,
  `currentSite` varchar(200) DEFAULT NULL,
  `fromDate` date DEFAULT NULL,
  `contractingCompany` varchar(100) DEFAULT NULL,
  `basicSalary` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`siteId`),
  KEY `FK_tbl_currentsite` (`empId`),
  CONSTRAINT `FK_tbl_currentsite` FOREIGN KEY (`empId`) REFERENCES `tbl_labourdetails` (`empId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_documents` */

DROP TABLE IF EXISTS `tbl_documents`;

CREATE TABLE `tbl_documents` (
  `empId` bigint(20) NOT NULL,
  `pic` longblob,
  `passportPic` longblob,
  `visaPic` longblob,
  PRIMARY KEY (`empId`),
  CONSTRAINT `FK_tbl_documents` FOREIGN KEY (`empId`) REFERENCES `tbl_labourdetails` (`empId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_employee` */

DROP TABLE IF EXISTS `tbl_employee`;

CREATE TABLE `tbl_employee` (
  `empId` bigint(20) NOT NULL AUTO_INCREMENT,
  `empName` varchar(100) DEFAULT NULL,
  `nationality` varchar(50) DEFAULT NULL,
  `profession` varchar(50) DEFAULT NULL,
  `passportNumber` varchar(50) DEFAULT NULL,
  `passportExpiry` date DEFAULT NULL,
  `visaExpiry` date DEFAULT NULL,
  `idNumber` varchar(50) DEFAULT NULL,
  `todayDate` date DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `currentSite` varchar(100) DEFAULT NULL,
  `firstParty` varchar(100) DEFAULT NULL,
  `secondParty` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_holiday` */

DROP TABLE IF EXISTS `tbl_holiday`;

CREATE TABLE `tbl_holiday` (
  `holidayId` bigint(20) NOT NULL AUTO_INCREMENT,
  `hMonth` varchar(20) DEFAULT NULL,
  `hYear` varchar(20) DEFAULT NULL,
  `holiday1` varchar(10) DEFAULT NULL,
  `holiday2` varchar(10) DEFAULT NULL,
  `holiday3` varchar(10) DEFAULT NULL,
  `holiday4` varchar(10) DEFAULT NULL,
  `holiday5` varchar(10) DEFAULT NULL,
  `holiday6` varchar(10) DEFAULT NULL,
  `holiday7` varchar(10) DEFAULT NULL,
  `holiday8` varchar(10) DEFAULT NULL,
  `holiday9` varchar(10) DEFAULT NULL,
  `holiday10` varchar(10) DEFAULT NULL,
  `holiday11` varchar(10) DEFAULT NULL,
  `holiday12` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`holidayId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_invoicedetails` */

DROP TABLE IF EXISTS `tbl_invoicedetails`;

CREATE TABLE `tbl_invoicedetails` (
  `invoiceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fromAdd` varchar(100) DEFAULT NULL,
  `toAdd` varchar(100) DEFAULT NULL,
  `invoiceNumber` varchar(100) DEFAULT NULL,
  `invoiceDate` date DEFAULT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  `InvoiceMonth` varchar(30) DEFAULT NULL,
  `invoiceYear` varchar(10) DEFAULT NULL,
  `terms` int(10) DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `balance` decimal(12,2) NOT NULL,
  `status` int(11) NOT NULL,
  `deduction` decimal(10,2) NOT NULL,
  PRIMARY KEY (`invoiceId`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_invoicedocument` */

DROP TABLE IF EXISTS `tbl_invoicedocument`;

CREATE TABLE `tbl_invoicedocument` (
  `invoiceDocumentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `invoiceId` bigint(20) DEFAULT NULL,
  `document` longblob,
  PRIMARY KEY (`invoiceDocumentId`),
  KEY `FK_tbl_invoicedocument` (`invoiceId`),
  CONSTRAINT `FK_tbl_invoicedocument` FOREIGN KEY (`invoiceId`) REFERENCES `tbl_invoicedetails` (`invoiceId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_invoicerecedivedocument` */

DROP TABLE IF EXISTS `tbl_invoicerecedivedocument`;

CREATE TABLE `tbl_invoicerecedivedocument` (
  `receivedDocumentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `receivedId` bigint(20) DEFAULT NULL,
  `document` longblob,
  PRIMARY KEY (`receivedDocumentId`),
  KEY `FK_tbl_invoicereceivedocument` (`receivedId`),
  CONSTRAINT `FK_tbl_invoicereceivedocument` FOREIGN KEY (`receivedId`) REFERENCES `tbl_invoicereceived` (`receivedId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_invoicereceived` */

DROP TABLE IF EXISTS `tbl_invoicereceived`;

CREATE TABLE `tbl_invoicereceived` (
  `receivedId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fromAdd` varchar(100) DEFAULT NULL,
  `toAdd` varchar(100) DEFAULT NULL,
  `invoiceNumber` varchar(100) DEFAULT NULL,
  `invoiceDate` date DEFAULT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  `invoiceMonth` varchar(20) DEFAULT NULL,
  `invoiceYear` varchar(10) DEFAULT NULL,
  `terms` int(10) DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`receivedId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_labourdetails` */

DROP TABLE IF EXISTS `tbl_labourdetails`;

CREATE TABLE `tbl_labourdetails` (
  `empId` bigint(50) NOT NULL AUTO_INCREMENT,
  `empName` varchar(50) NOT NULL,
  `nationality` varchar(50) NOT NULL,
  `profession` varchar(50) NOT NULL,
  `passportNumber` varchar(50) NOT NULL,
  `passportExpiry` date NOT NULL,
  `visaExpiry` date NOT NULL,
  `idNumber` varchar(50) NOT NULL,
  `todayDate` date NOT NULL,
  `dob` date NOT NULL,
  `currentSite` varchar(100) NOT NULL,
  `firstParty` varchar(100) NOT NULL,
  `secondParty` varchar(100) NOT NULL,
  `contractingCompany` varchar(100) DEFAULT NULL,
  `basicSalary` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_payment` */

DROP TABLE IF EXISTS `tbl_payment`;

CREATE TABLE `tbl_payment` (
  `paymentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `voucherDate` date DEFAULT NULL,
  `paidTo` varchar(500) DEFAULT NULL,
  `ByCashOrCheckNo` varchar(100) DEFAULT NULL,
  `dated` date DEFAULT NULL,
  `bank` varchar(200) DEFAULT NULL,
  `being` varchar(200) DEFAULT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  `theSumOf` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`paymentId`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_receipt` */

DROP TABLE IF EXISTS `tbl_receipt`;

CREATE TABLE `tbl_receipt` (
  `receiptId` bigint(20) NOT NULL AUTO_INCREMENT,
  `voucherDate` date DEFAULT NULL,
  `receivedFrom` varchar(200) DEFAULT NULL,
  `ByCashOrCheckNo` varchar(100) DEFAULT NULL,
  `dated` date DEFAULT NULL,
  `bank` varchar(200) DEFAULT NULL,
  `being` varchar(200) DEFAULT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  `theSumOf` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`receiptId`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=latin1;

/*Table structure for table `tbl_timesheet` */

DROP TABLE IF EXISTS `tbl_timesheet`;

CREATE TABLE `tbl_timesheet` (
  `timeSheetId` bigint(20) NOT NULL AUTO_INCREMENT,
  `empId` bigint(20) DEFAULT NULL,
  `timeSheetMonth` varchar(30) DEFAULT NULL,
  `timeSheetYear` bigint(20) DEFAULT NULL,
  `f1` varchar(10) DEFAULT NULL,
  `f2` varchar(10) DEFAULT NULL,
  `f3` varchar(10) DEFAULT NULL,
  `f4` varchar(10) DEFAULT NULL,
  `f5` varchar(10) DEFAULT NULL,
  `f6` varchar(10) DEFAULT NULL,
  `f7` varchar(10) DEFAULT NULL,
  `f8` varchar(10) DEFAULT NULL,
  `f9` varchar(10) DEFAULT NULL,
  `f10` varchar(10) DEFAULT NULL,
  `f11` varchar(10) DEFAULT NULL,
  `f12` varchar(10) DEFAULT NULL,
  `f13` varchar(10) DEFAULT NULL,
  `f14` varchar(10) DEFAULT NULL,
  `f15` varchar(10) DEFAULT NULL,
  `f16` varchar(10) DEFAULT NULL,
  `f17` varchar(10) DEFAULT NULL,
  `f18` varchar(10) DEFAULT NULL,
  `f19` varchar(10) DEFAULT NULL,
  `f20` varchar(10) DEFAULT NULL,
  `f21` varchar(10) DEFAULT NULL,
  `f22` varchar(10) DEFAULT NULL,
  `f23` varchar(10) DEFAULT NULL,
  `f24` varchar(10) DEFAULT NULL,
  `f25` varchar(10) DEFAULT NULL,
  `f26` varchar(10) DEFAULT NULL,
  `f27` varchar(10) DEFAULT NULL,
  `f28` varchar(10) DEFAULT NULL,
  `f29` varchar(10) DEFAULT NULL,
  `f30` varchar(10) DEFAULT NULL,
  `f31` varchar(10) DEFAULT NULL,
  `totalNormal` decimal(5,2) DEFAULT NULL,
  `toalOT` decimal(5,2) DEFAULT NULL,
  `totalHOT` decimal(5,2) DEFAULT NULL,
  `normalRatePerHour` decimal(5,2) DEFAULT NULL,
  `otRatePerHour` decimal(5,2) DEFAULT NULL,
  `hotRatePerHour` decimal(5,2) DEFAULT NULL,
  `grossAmountNormal` decimal(7,2) DEFAULT NULL,
  `grossAmountOT` decimal(7,2) DEFAULT NULL,
  `grossAmountHOT` decimal(7,2) DEFAULT NULL,
  `countLeave` int(11) DEFAULT NULL,
  `countAbsent` int(11) DEFAULT NULL,
  `grossAmount` decimal(7,2) DEFAULT NULL,
  `foodAddition` decimal(7,2) DEFAULT NULL,
  `dueAddition` decimal(7,2) DEFAULT NULL,
  `bonusAddition` decimal(5,2) DEFAULT NULL,
  `otherAddition` decimal(7,2) DEFAULT NULL,
  `absentDeduction` decimal(7,2) DEFAULT NULL,
  `advanceDeduction` decimal(7,2) DEFAULT NULL,
  `foodDeduction` decimal(7,2) DEFAULT NULL,
  `otherDeduction` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`timeSheetId`),
  KEY `FK_tbl_timesheet` (`empId`),
  CONSTRAINT `FK_tbl_timesheet` FOREIGN KEY (`empId`) REFERENCES `tbl_labourdetails` (`empId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
