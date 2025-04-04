from .api_client_base import APIClientBase

class CouponAPIClient(APIClientBase):
    def get_coupon_list(self):
        """
        获取优惠券列表
        :return: 响应对象
        """
        url = "/api/front/coupon/list"
        return self._send_request('GET', url)

    def receive_coupon(self, coupon_id):
        """
        领取优惠券
        :param coupon_id: 优惠券 ID
        :return: 响应对象
        """
        url = f"/api/front/coupon/receive/{coupon_id}"
        return self._send_request('POST', url)