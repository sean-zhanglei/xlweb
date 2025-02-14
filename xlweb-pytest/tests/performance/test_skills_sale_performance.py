from locust import HttpUser, task, between
from config import BASE_URL

class SkillsSalePerformanceUser(HttpUser):
    host = BASE_URL  # 替换为你要测试的实际主机地址
    wait_time = between(1, 5)

    # @task
    # def participate_flash_sale(self):
    #     # 假设秒杀接口路径
    #     url = f"{BASE_URL}/api/front/flash_sale/participate"
    #     data = {
    #         "product_id": 3,
    #         "quantity": 1
    #     }
    #     self.client.post(url, headers=self.headers, json=data)