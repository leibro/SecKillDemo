CREATE DATABASE seckill;
use seckill;

CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '库存数量',
`start_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
`end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT = 10000 DEFAULT  CHARSET=utf8 COMMENT='秒杀库存表';

INSERT INTO seckill(name,number,start_time,end_time)
VALUES
  ('1000元秒杀iPhone6',100,'2017-04-08 23:00:00','2017-04-08 23:10:00'),
  ('500元秒杀iPad2',200,'2017-04-08 21:00:00','2017-04-08 21:10:00'),
  ('300元秒杀小米4',300,'2017-04-09 21:00:00','2017-04-09 21:10:00'),
  ('200元秒杀红米note4',400,'2017-04-10 21:00:00','2017-04-11 22:00:00');

CREATE TABLE success_killed(
`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL DEFAULT -1 COMMENT '状态标识：-1无效 0成功 1已付款',
`create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id,user_phone),
KEY idx_create_time(create_time)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

mysql -uroot -p