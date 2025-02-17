# 订单列表页面场景
import time
from locust import HttpUser, task, between
from locust import events
from config import BASE_URL
from tests import logger
from utils.address_api_client import APIClientBase


# 初始化响应数据总和变量
total_received_bytes = 0

# 定义请求成功时的处理函数
@events.request.add_listener
def on_request(request_type, name, response_time, response_length, **kwargs):
    global total_received_bytes
    # 累加响应数据长度
    total_received_bytes += response_length

# 定义测试结束时的处理函数
@events.test_stop.add_listener
def on_test_stop(environment, **kwargs):
    global total_received_bytes
    logger.info(f"Total received bytes: {total_received_bytes}")

class OrderListPerformanceUser(HttpUser):
    host = BASE_URL  # 替换为你要测试的实际主机地址
    wait_time = between(0.1, 1)
    
    def on_start(self):
        api_client_base = APIClientBase()
        self.headers = api_client_base.headers

    @task
    def OrderList(self, page=1, page_size=10):
        start_time = time.time()  # 记录请求开始时间
        
        url = f"{BASE_URL}/api/front/order/list"
        headers = {
            **self.headers,
            "Content-Type": "application/json"
        }
        params = {
            "type": 4, # 评价等级|0=未支付,1=待发货,2=待收货,3=待评价,4=已完成,-3=售后/退款
            "page": page,
            "page_size": page_size
        }
        response = self.client.get(url, params=params, headers=headers)
        try:
            # 断言响应时间不超过 2000 毫秒（即 2 秒）
            assert response.elapsed.total_seconds() * 1000 <= 2000, f"响应时间超过 2 秒: {response.elapsed.total_seconds() * 1000} 毫秒"
        except AssertionError as e:
            # 使用新的事件触发方式处理请求失败
            events.request.fire(
                request_type="GET",
                name=response.request.path_url,
                response_time=response.elapsed.total_seconds() * 1000,
                response_length=len(response.content),
                exception=e,
                context={},
                start_time=start_time
            )
        # logger.info(f"获取订单列表数据: {response.json()}")