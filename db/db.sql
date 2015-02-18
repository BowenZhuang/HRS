Create Database pInfo;

use pInfo;

DROP TABLE IF EXISTS plog;
CREATE TABLE IF NOT EXISTS plog (
  id INT PRIMARY KEY AUTO_INCREMENT,
  pname VARCHAR(160) NOT NULL,
  dob varchar(20) not null,
  phone varchar(20) not null,
  bCalled boolean not null,
  bSentSMS boolean not null
)ENGINE = MyISAM;