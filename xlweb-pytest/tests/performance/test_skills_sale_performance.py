# 秒杀场景
import time
from locust import HttpUser, task, between
from locust import events
from config import BASE_URL
from tests import logger
from utils.api_client_base import APIClientBase

class SkillsSalePerformanceUser(HttpUser):
    host = BASE_URL  # 替换为你要测试的实际主机地址
    wait_time = between(0.1, 1)
    
    def on_start(self):
        api_client_base = APIClientBase()
        self.headers = api_client_base.headers

    def _request(self, url, method='GET', params = None, json=None):
        headers = {
            **self.headers,
            "Content-Type": "application/json"
        }
        start_time = time.time()  # 记录请求开始时间
        if method == 'GET':
            response = self.client.get(url, params = params,headers=headers)
        elif method == 'POST':
            response = self.client.post(url, params=params , json=json, headers=headers)
        else:
            raise ValueError("Unsupported method")
        
        try:
            # 断言响应时间不超过 2000 毫秒（即 2 秒）
            assert response.elapsed.total_seconds() * 1000 <= 2000, f"响应时间超过 2 秒: {response.elapsed.total_seconds() * 1000} 毫秒"
        except AssertionError as e:
            # 使用新的事件触发方式处理请求失败
            events.request.fire(
                request_type=method,
                name=response.request.path_url,
                response_time=response.elapsed.total_seconds() * 1000,
                response_length=len(response.content),
                exception=e,
                context={},
                start_time=start_time
            )
            logger.error(f"请求失败: {e}")
            return None
        
        logger.info(f"请求成功: {response.json()}")
        return response

    @task
    def skill(self):
        # 秒杀列表
        time_id = 2
        url = f"{BASE_URL}/api/front/seckill/list/{time_id}"
        params = {
            "page": 1,
            "limit":  10
        }
        response = self._request(url, params = params)
        if not response:
            return
        # logger.info(f"获取秒杀列表信息数据: {response.json()}")
        
        # 获取0号秒杀商品
        assert response.status_code == 200, f"获取秒杀商品列表失败，status_code: {response.status_code}"
        assert len(response.json().get("data").get("list", [])) > 0, f"当前账户秒杀商品列表为空，data: {response.json()}"
        # 商品ID
        skill_product_id = response.json().get("data").get("list")[0].get("id") 
        
        # 获取0号秒杀商品详情
        url = f"{BASE_URL}/api/front/seckill/detail/{skill_product_id}"
        response = self._request(url)
        
        # 秒杀商品属性ID
        product_value_id = response.json().get("data").get("productValue").get("默认").get("id")
        # 商品ID
        product_id = response.json().get("data").get("storeSeckill").get("productId")
        
        if not response:
            return
        logger.info(f"获取0号秒杀商品详情信息数据: {response.json()}")
        
        # 预下单，生成预下单ID
        url = f"{BASE_URL}/api/front/order/pre/order"
        json = {
            "preOrderType": "buyNow",
            "orderDetails": [
                {
                    "attrValueId": product_value_id, # 商品属性值ID
                    "seckillId": skill_product_id, # 秒杀商品ID
                    "productNum": 1,  # 购买数量
                    "productId": product_id # 商品ID
                }
            ]
        }
        response = self._request(url, method='POST', json=json)
        if not response:
            return
        logger.info(f"获取预下单结果信息数据: {response.json()}")
        
        # 预下单ID
        pre_order_no = response.json().get("data").get("preOrderNo")
        
        # 预下单详情
        url = f"{BASE_URL}/api/front/order/load/pre/{pre_order_no}"
        response = self._request(url)
        if not response:
            return
        logger.info(f"获取预下单详情数据: {response.json()}")
        
        # 计算商品价格
        url = f"{BASE_URL}/api/front/order/computed/price"
        json = {
            "addressId": 1, # 地址ID, 默认
            "useIntegral": "false", # 默认
            "couponId": 0, # 优惠券ID, 默认
            "shippingType": 2, # 1: 商家配送, 2: 自提
            "preOrderNo": pre_order_no # 预下单ID
        }
        response = self._request(url, method='POST', json=json)
        if not response:
            return
        logger.info(f"获取商品价格数据: {response.json()}")
        
        # 创建订单
        
        url = f"{BASE_URL}/api/front/order/create"
        json = {
            "realName": "张xxx",
            "phone": "15719456470",
            "addressId": 1,
            "couponId": 0,
            "payType": "yue", # 支付方式 weixin 微信| yue 余额 | offline 线下| alipay 支付宝
            "useIntegral": "false",
            "preOrderNo": pre_order_no,
            "mark": "",
            "storeId": 1,
            "shippingType": 2,  # 1: 商家配送, 2: 自提
            "payChannel": "weixinh5" # weixinh5-微信H5支付，public-公众号支付，routine-小程序支付，weixinAppIos-微信appios支付，weixinAppAndroid-微信app安卓支付,alipay-支付包支付，appAliPay-App支付宝支付
        }
        response = self._request(url, method='POST', json=json)
        if not response:
            return
        logger.info(f"获取创建订单结果数据: {response.json()}")
        
        # 订单ID
        order_no = response.json().get("data").get("orderNo");
        
        # 支付订单
        url = f"{BASE_URL}/api/front/pay/payment"
        json = {
            "orderNo": order_no,
            "payChannel": "weixinh5",
            "payType": "yue",
            "scene": 0 # 下单时小程序的场景值
        }
        response = self._request(url, method='POST', json=json)
        if not response:
            return
        logger.info(f"获取订单支付结果数据: {response.json()}")
        
        # 订单详情
        url = f"{BASE_URL}/api/front/order/detail/{order_no}"
        response = self._request(url)
        if not response:
            return
        logger.info(f"获取订单详情数据: {response.json()}")