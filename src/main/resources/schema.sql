CREATE TABLE IF NOT EXISTS category (
                                        id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(20) NOT NULL
) COMMENT = '카테고리';

CREATE TABLE IF NOT EXISTS pb (
                                  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  password VARCHAR(20) NOT NULL COMMENT '비밀번호',
                                  name VARCHAR(20) NOT NULL COMMENT '이름',
                                  image_url VARCHAR(250) COMMENT '프로필 이미지',
                                  introduce VARCHAR(300) NOT NULL COMMENT '한줄소개',
                                  office VARCHAR(300) NOT NULL COMMENT '사무실',
                                  career TEXT COMMENT '경력',
                                  login_id VARCHAR(20) NOT NULL COMMENT '사번',
                                  tel VARCHAR(20) NOT NULL  COMMENT '연락처',
                                  availability BOOLEAN NOT NULL COMMENT '상담가능여부'
) COMMENT = 'PB';

CREATE TABLE IF NOT EXISTS keyword (
                                       id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       title VARCHAR(100) NOT NULL COMMENT '제목',
                                       content TEXT NOT NULL  COMMENT '내용'
) COMMENT = '키워드';

CREATE TABLE IF NOT EXISTS customer (
                                        id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        pb_id INT UNSIGNED COMMENT 'PB아이디',
                                        name VARCHAR(20) NOT NULL COMMENT '이름',
                                        email VARCHAR(20) NOT NULL COMMENT '이메일',
                                        password VARCHAR(30) NOT NULL COMMENT '비밀번호',
                                        tel VARCHAR(20) NOT NULL COMMENT '연락처',
                                        address VARCHAR(30) NOT NULL COMMENT '주소',
                                        date DATE NOT NULL COMMENT '매칭날짜',
                                        count INT UNSIGNED COMMENT '상담횟수',
                                        memo VARCHAR(50) COMMENT '메모',
                                        CONSTRAINT fk_customer_pb FOREIGN KEY (pb_id) REFERENCES pb (id)
) COMMENT = 'VIP손님';

CREATE TABLE IF NOT EXISTS consulting (
                                          id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          customer_id INT UNSIGNED COMMENT '손님아이디',
                                          category_id INT UNSIGNED COMMENT '카테고리아이디',
                                          title VARCHAR(50) COMMENT '제목',
                                          hope_date DATE NOT NULL COMMENT '희망상담날짜',
                                          hope_time TIME NOT NULL COMMENT '희망상담시간',
                                          reserve_date DATE NOT NULL COMMENT '요청날짜',
                                          reserve_time TIME NOT NULL COMMENT '요청시간',
                                          content VARCHAR(500) NOT NULL COMMENT '내용',
                                          approve BOOLEAN NOT NULL COMMENT '승인여부',
                                          CONSTRAINT fk_consulting_category FOREIGN KEY (category_id) REFERENCES category (id),
                                          CONSTRAINT fk_consulting_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
) COMMENT = '상담';

CREATE TABLE IF NOT EXISTS hashtag (
                                       id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       pb_id INT UNSIGNED NOT NULL COMMENT 'PB아이디',
                                       name VARCHAR(20) NOT NULL COMMENT '해시태그명',
                                       CONSTRAINT fk_hashtag_pb FOREIGN KEY (pb_id) REFERENCES pb (id)
) COMMENT = '해시태그';

CREATE TABLE IF NOT EXISTS journal (
                                       id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       consulting_id INT UNSIGNED COMMENT '상담아이디',
                                       contents TEXT NOT NULL COMMENT '내용',
                                       complete BOOLEAN NOT NULL COMMENT '전송여부',
                                       CONSTRAINT fk_journal_consulting UNIQUE (consulting_id),
                                       CONSTRAINT fk_journal_consulting_id FOREIGN KEY (consulting_id) REFERENCES consulting (id)
) COMMENT = '상담일지';

CREATE TABLE IF NOT EXISTS issue (
                                     id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     keyword_id INT UNSIGNED NOT NULL COMMENT '키워드아이디',
                                     title VARCHAR(100) NOT NULL COMMENT '제목',
                                     issue_url VARCHAR(250) NOT NULL COMMENT '이슈URL',
                                     image_url VARCHAR(250) NOT NULL COMMENT '이미지URL',
                                     CONSTRAINT fk_issue_keyword FOREIGN KEY (keyword_id) REFERENCES keyword (id)
) COMMENT = '이슈';

CREATE TABLE IF NOT EXISTS notification (
                                            id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                            customer_id INT UNSIGNED COMMENT '손님아이디',
                                            title VARCHAR(30) NOT NULL COMMENT '제목',
                                            content TEXT NOT NULL COMMENT '내용',
                                            date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '날짜',
                                            is_read BOOLEAN NOT NULL COMMENT '읽음여부',
                                            CONSTRAINT fk_notification_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
) COMMENT = '알림';

CREATE TABLE IF NOT EXISTS journal_keyword (
                                               id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                               customer_id INT UNSIGNED NOT NULL,
                                               journal_id INT UNSIGNED NOT NULL,
                                               keyword_id INT UNSIGNED NOT NULL,
                                               CONSTRAINT fk_journal_keyword_customer FOREIGN KEY (customer_id) REFERENCES customer (id),
                                               CONSTRAINT fk_journal_keyword_journal FOREIGN KEY (journal_id) REFERENCES journal (id),
                                               CONSTRAINT fk_journal_keyword_keyword FOREIGN KEY (keyword_id) REFERENCES keyword (id)
) COMMENT = '상담일지키워드';

CREATE TABLE IF NOT EXISTS product (
                                       id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(100) NOT NULL,
                                       product_url VARCHAR(250) NOT NULL,
                                       image_url VARCHAR(250) NOT NULL
) COMMENT = '추천상품';

CREATE TABLE IF NOT EXISTS journal_product (
                                               id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                               journal_id INT UNSIGNED NOT NULL,
                                               product_id INT UNSIGNED NOT NULL,
                                               CONSTRAINT fk_journal_product_journal FOREIGN KEY (journal_id) REFERENCES journal (id),
                                               CONSTRAINT fk_journal_product_product FOREIGN KEY (product_id) REFERENCES product (id)
) COMMENT = '상담일지상품';

CREATE TABLE IF NOT EXISTS script (
                                      id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      journal_id INT UNSIGNED COMMENT '상담일지아이디',
                                      script_sequence INT UNSIGNED NOT NULL COMMENT '순서',
                                      speaker TEXT NOT NULL COMMENT '발화자',
                                      content LONGTEXT NOT NULL COMMENT '내용',
                                      CONSTRAINT fk_script_journal FOREIGN KEY (journal_id) REFERENCES journal (id)
) COMMENT = '스크립트';
