# 大作业

## 生鲜商超

问题:1 .图片未设置
2.***ui设置改进***
3.用户操作定义
------
设置细节:

菜谱推荐表作用:买商品时显示菜谱, 搜菜谱时推荐商品

生鲜表中是对商品具体类别的描述 
优惠: ***设置时一般折扣和促销二选一(适用同一商品)取价格低的***, 可以在此基础上根据金额再使用优惠券(可以跨商品)
不应该三种优惠同时出现(根据实际电商情况)

订单详情表目的是得到原始价格(很像一个视图, 主键(订单编号,商品编号)),不包含优惠券
订单详情表（订单编号，商品编号，用户编号,数量，价格，vip价格,折扣编号,折扣,促销编号,促销价格,订单状态("待支付","已支付","已取消"))
商品订单表（订单编号，用户编号，原始金额，结算金额，使用优惠券编号，要求送达时间，配送地址编号，订单状态("待支付","已支付","已取消"))

***cart->order_detail->order***

商品购买只能先加入购物车,在购物车里购买
(加入购物车->支付->详细订单)


------

采购表类似于仓库  状态:
1. 管理员采购时,若商品表不存在,设置为新货,否则为下单 (增) 
2. 到货时设置为("新货"->"新货入库"),("下单"->"入库")(改)
3. 增加商品时,  从入库状态 变为上架                (增)

