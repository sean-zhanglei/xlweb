# 登录接口压测

from locust import HttpUser, task, between
from locust import events
from config import BASE_URL
from tests import logger
import csv
import random

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

# 读取 CSV 文件中的用户名和密码
users = []
with open('users.csv', 'r') as file:
    reader = csv.DictReader(file)
    for row in reader:
        users.append(row)

class LoginPerformanceUser(HttpUser):
    host = BASE_URL  # 替换为你要测试的实际主机地址
    wait_time = between(0.1, 1)
    
    def on_start(self):
        # 当每个用户启动时，从用户列表中随机选择一个用户名和密码
        if users:
            self.user = random.choice(users)


    @task
    def login(self):
        url = f"{BASE_URL}/api/front/login"
        data = {
            "account": self.user['username'],
            "password": self.user['password']
        }
        response = self.client.post(url, json=data)
        logger.info(f"登录结果: {response.json()}")