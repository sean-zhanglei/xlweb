from .api_client_base import APIClientBase

class AddressAPIClient(APIClientBase):
    def get_default_address(self):
        """
        获取默认地址
        :return: 响应对象
        """
        url = "/api/front/address/default"
        return self._send_request('GET', url)

    def set_default_address(self, data):
        """
        设置默认地址
        :param data: 请求体数据，通常为 UserAddressDelRequest 对象相关数据
        :return: 响应对象
        """
        url = "/api/front/address/default/set"
        headers = {
            **self.headers,
            "Content-Type": "application/json"
        }
        return self._send_request('POST', url, json=data)

    def get_address_list(self):
        """
        获取地址列表
        :return: 响应对象
        """
        url = "/api/front/address/list"
        return self._send_request('GET', url)

    def add_address(self, data):
        """
        添加地址
        :param data: 请求体数据，包含地址信息
        :return: 响应对象
        """
        url = "/api/front/address/add"
        headers = {
            **self.headers,
            "Content-Type": "application/json"
        }
        return self._send_request('POST', url, json=data)

    def update_address(self, address_id, data):
        """
        更新地址信息
        :param address_id: 地址 ID
        :param data: 请求体数据，包含要更新的地址信息
        :return: 响应对象
        """
        url = f"/api/front/address/update/{address_id}"
        headers = {
            **self.headers,
            "Content-Type": "application/json"
        }
        return self._send_request('PUT', url, json=data)

    def delete_address(self, address_id):
        """
        删除地址
        :param address_id: 地址 ID
        :return: 响应对象
        """
        url = f"/api/front/address/delete/{address_id}"
        return self._send_request('DELETE', url)