from .api_client_base import APIClientBase

class CartAPIClient(APIClientBase):
    def add_product_to_cart(self, product_id, quantity=1):
        """
        将商品添加到购物车
        :param product_id: 商品 ID
        :param quantity: 商品数量，默认为 1
        :return: 响应对象
        """
        url = "/api/front/cart/add"
        headers = {
            **self.headers,
            "Content-Type": "application/json"
        }
        data = {
            "product_id": product_id,
            "quantity": quantity
        }
        return self._send_request('POST', url, json=data)

    def get_cart_list(self):
        """
        获取购物车列表
        :return: 响应对象
        """
        url = "/api/front/cart/list"
        return self._send_request('GET', url)

    def update_cart_item(self, cart_item_id, quantity):
        """
        更新购物车商品数量
        :param cart_item_id: 购物车项 ID
        :param quantity: 新的商品数量
        :return: 响应对象
        """
        url = f"/api/front/cart/update/{cart_item_id}"
        headers = {
            **self.headers,
            "Content-Type": "application/json"
        }
        data = {
            "quantity": quantity
        }
        return self._send_request('PUT', url, json=data)

    def delete_cart_item(self, cart_item_id):
        """
        删除购物车中的商品
        :param cart_item_id: 购物车项 ID
        :return: 响应对象
        """
        url = f"/api/front/cart/delete/{cart_item_id}"
        return self._send_request('DELETE', url)