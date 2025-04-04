import requests
import logging
from config import BASE_URL, USERNAME, PASSWORD

# 配置日志
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

class SingletonMeta(type):
    _instances = {}

    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super().__call__(*args, **kwargs)
        return cls._instances[cls]

class APIClientBase(metaclass=SingletonMeta):
    MAX_RETRIES = 3  # 最大重试次数

    def __init__(self):
        self.base_url = BASE_URL
        self.headers = {}
        self._token = None
        retries = 0
        while retries < self.MAX_RETRIES:
            try:
                self._token = self.login(USERNAME, PASSWORD)
                if self._token:
                    self.headers["Authori-zation"] = self._token
                    break
            except Exception as e:
                logging.error(f"登录时发生异常: {e}")
            retries += 1
            if retries < self.MAX_RETRIES:
                logging.warning(f"登录失败，正在进行第 {retries + 1} 次重试...")

        if not self._token:
            logging.error("登录失败，达到最大重试次数")

    def _send_request(self, method, url, params=None, data=None, json=None):
        full_url = f"{self.base_url}{url}"
        return requests.request(method, full_url, headers=self.headers, params=params, data=data, json=json)

    def login(self, username, password):
        url = "/api/front/login"  # 假设登录接口路径
        data = {
            "account": username,
            "password": password
        }
        response = self._send_request('POST', url, json=data)
        if response.status_code == 200:
            res = response.json();
            res_data = res.get("data");
            return res_data.get("token")
        logging.error(f"登录失败，状态码: {response.status_code}, 响应内容: {response.text}")
        return None