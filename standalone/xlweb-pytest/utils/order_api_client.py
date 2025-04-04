from .api_client_base import APIClientBase

class OrderAPIClient(APIClientBase):
    def create_order(self, data):
        """
        创建订单
        :param data: 请求体数据，包含订单相关信息
        :return: 响应对象
        """
        url = "/api/front/order/create"
        headers = {
            **self.headers,
            "Content-Type": "application/json"
        }
        return self._send_request('POST', url, json=data)

    def get_order_list(self, page=1, page_size=10):
        """
        获取订单列表
        :param page: 页码，默认为 1
        :param page_size: 每页数量，默认为 10
        :return: 响应对象
        """
        url = "/api/front/order/list"
        params = {
            "page": page,
            "page_size": page_size
        }
        return self._send_request('GET', url, params=params)

    def get_order_detail(self, order_id):
        """
        获取订单详情
        :param order_id: 订单 ID
        :return: 响应对象
        """
        url = f"/api/front/order/detail/{order_id}"
        return self._send_request('GET', url)