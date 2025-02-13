-- 创建库
create database if not exists tail_yy;

-- 切换库
use tail_yy;

-- 用户表 user
create table if not exists user
(
    id           bigint auto_increment                  comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    avatar       varchar(1024)                          null comment '用户头像',
    userRole     char(1) default '2'                    not null comment '用户角色：0 店长 1店员 2 顾客',
    password     varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) character set = utf8 comment '用户';


-- 宠物表 pet
create table if not exists pet
(
    id           bigint auto_increment                  comment 'id' primary key,
    petName      varchar(256)                           null comment '宠物名称',
    class_id        varchar(256)                        not null comment '宠物种类',
    info         varchar(1024)                          null comment '宠物信息',
    pic          varchar(1024)                          not null comment '宠物照片',
    price        double                                 not null comment '价格',
    body_record  text                                   comment '健康状态记录',
    service_record  text                                comment '服务记录',
    zone_id      int                                    comment '笼子号',
    status      int                                    not null comment '状态 0在售 1已售',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) character set = utf8  comment '宠物';

-- 插入第一条宠物记录
INSERT INTO pet (petName, class_id, info, pic, price, body_record, service_record, zone_id, status)
VALUES ('小黄', '5', '一只活泼可爱的小狗', '/images/pets/dog1.jpg', 500.00, '[{"bodyStatus" : "良好","time" : "2025-01-12 00:28:46","remark" : "测试"}]', '[{"serviceName" : "洗澡","assistantName" : "2025-01-12 00:29:46","serviceTime" : "2025-01-12 00:30:46"}]', 1, 0);

-- 插入第二条宠物记录
INSERT INTO pet (petName, class_id, info, pic, price, body_record, service_record, zone_id, status)
VALUES ('咪咪', '6', '性格温顺的一只小猫', '/images/pets/cat1.jpg', 600.00, '[{"bodyStatus" : "良好","time" : "2025-01-12 00:28:46","remark" : "测试"}]', '[{"serviceName" : "洗澡","assistantName" : "2025-01-12 00:29:46","serviceTime" : "2025-01-12 00:30:46"}]', 2, 1);

# -- 插入第三条宠物记录
# INSERT INTO pet (petName, class_id, info, pic, price, body_record, service_record, zone_id, status)
# VALUES ('小白', '2', '非常可爱的白兔', '/images/pets/rabbit1.jpg', 200.00, '[{"bodyStatus" : "良好","time" : "2025-01-12 00:28:46","remark" : "测试"}]', '[{"serviceName" : "洗澡","assistantName" : "2025-01-12 00:29:46","serviceTime" : "2025-01-12 00:30:46"}]', 3, 0);
#
# -- 插入第四条宠物记录
# INSERT INTO pet (petName, class_id, info, pic, price, body_record, service_record, zone_id, status)
# VALUES ('彩霞', '鸟类', '羽毛色彩斑斓的鹦鹉', '/images/pets/bird1.jpg', 400.00, '[{"bodyStatus" : "良好","time" : "2025-01-12 00:28:46","remark" : "测试"}]', '[{"serviceName" : "洗澡","assistantName" : "2025-01-12 00:29:46","serviceTime" : "2025-01-12 00:30:46"}]', 4, 0);


-- 用户和宠物关系表 pet_with_user
create table if not exists pet_with_user
(
    pet_id           int                  comment '宠物id' primary key,
    user_id           int                 not null comment '用户id',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) character set = utf8  comment '用户和宠物关系';

-- 宠物用品 & 宠物服务表 commodity
create table if not exists commodity
(
    id           bigint auto_increment                  comment 'id' primary key,
    commodity    varchar(256)                           null comment '商品名称',
    info         text                                   null comment '宠物信息',
    pic          varchar(1024)                          not null comment '宠物照片',
    price        double                                 not null comment '价格',
    commodity_type  char(1)                             not null comment '商品类型 0 宠物用品 1 宠物服务',
    class_id        int                                    not null comment '商品种类id',
    stock_count  int                                    not null comment '库存 宠物服务没有库存',
    stock_remind int                                    comment '最低库存提醒（宠物服务没有）',
    status       char(1)                                not null comment '状态 0 正常， 1 关闭',
    comments     text                                   comment '评价列表',
    expire_time  datetime                               comment '过期时间（指这个商品的售后过期时间）',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
)  character set = utf8 comment '宠物用品 & 宠物服务';

INSERT INTO commodity (commodity, info, pic, price, commodity_type, class_id, stock_count, stock_remind, status, comments)
VALUES ('宠物玩具', '适合所有年龄段的猫狗', '/images/commodities/toy.jpg', 29.99, '0', 1, 100, 10, '0',
        '[
            {"userid": 1, "isBuyer": false, "comment": "我的猫很喜欢这个玩具！", "date": "2024-12-01 00:28:46"}
        ]');


-- 类别表 class
create table if not exists class
(
    id           bigint auto_increment                  comment 'id' primary key,
    class_type   char(1)                                not null comment '类型 0 宠物用品，1 宠物服务，2 宠物',
    class_name   varchar(256)                           not null comment '类别名称',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) character set = utf8 comment '类别';

-- 插入第一条类别记录（宠物用品）
INSERT INTO class (class_type, class_name)
VALUES ('0', '狗粮');

-- 插入第二条类别记录（宠物用品）
INSERT INTO class (class_type, class_name)
VALUES ('0', '猫砂');

-- 插入第三条类别记录（宠物服务）
INSERT INTO class (class_type, class_name)
VALUES ('1', '宠物美容');

-- 插入第四条类别记录（宠物服务）
INSERT INTO class (class_type, class_name)
VALUES ('1', '宠物寄养');

-- 插入第五条类别记录（宠物）
INSERT INTO class (class_type, class_name)
VALUES ('2', '狗');

-- 插入第六条类别记录（宠物）
INSERT INTO class (class_type, class_name)
VALUES ('2', '猫');

-- 订单表 order
create table if not exists order_info
(
    id           bigint auto_increment                  comment '订单号' primary key,
    user_id      int                                    not null comment '用户id',
    goods_id     int                                    not null comment '商品id（宠物或者商品）',
    class_id     int                                    not null comment '商品种类id',
    count        int                                    not null comment '商品数量',
    operate_id   int                                    not null comment '操作者id（店员或者店长）',
    price        double                                 not null comment '价格',
    address      varchar(255)                           not null comment '订单地址',
    order_type   char(1)                                not null comment '订单类型 0线上 1线下',
    comments     text                                   comment '订单评价(会同步到对应的商品表中)',
    cur_status   char(1)                                not null comment '当前订单状态',
    pre_status   char(1)                                comment '订单上一个状态',
    cancelOrRefund char(1)                              comment '退货还是换货',
    req_status   char(1)                                comment '退换货状态',
    service_time datetime                               comment '售后服务截止时间',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '下单时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) character set = utf8 comment '订单';

-- 插入第一条订单记录
INSERT INTO order_info (user_id, goods_id, class_id, count, operate_id, price, address, order_type, cur_status)
VALUES (1, 101, 1, 1, 1, 29.99, '上海市浦东新区陆家嘴环路1000号', '0', '0 ');
