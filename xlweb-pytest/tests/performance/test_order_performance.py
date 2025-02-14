from locust import HttpUser, task, between
from config import BASE_URL

class OrderPerformanceUser(HttpUser):
    host = BASE_URL  # 替换为你要测试的实际主机地址
    wait_time = between(1, 5)
    # @task
    # def create_order(self):
    #     url = f"{BASE_URL}/api/front/order/create"
    #     data = {
    #         # 根据实际接口要求填写订单数据
    #         "product_ids": [1, 2],
    #         "address_id": 1
    #     }
    #     self.client.post(url, headers=self.headers, json=data)