from .api_client_base import APIClientBase

class ProductAPIClient(APIClientBase):
    
    def get_product_category(self):
        """
        获取商品分类
        :return: 响应对象
        """
        url = "/api/front/category"
        params = {}
        response = self._send_request("GET", url, params=params)
        return response
    def get_product_list(self, page=1, page_size=10):
        """
        获取商品列表
        :param page: 页码，默认为 1
        :param page_size: 每页数量，默认为 10
        :return: 响应对象
        """
        url = "/api/front/product/list"
        params = {
            "page": page,
            "page_size": page_size
        }
        return self._send_request('GET', url, params=params)

    def get_product_detail(self, product_id):
        """
        获取商品详情
        :param product_id: 商品 ID
        :return: 响应对象
        """
        url = f"/api/front/product/detail/{product_id}"
        return self._send_request('GET', url)

    def search_products(self, keyword, page=1, page_size=10):
        """
        搜索商品
        :param keyword: 搜索关键词
        :param page: 页码，默认为 1
        :param page_size: 每页数量，默认为 10
        :return: 响应对象
        """
        url = "/api/front/product/search"
        params = {
            "keyword": keyword,
            "page": page,
            "page_size": page_size
        }
        return self._send_request('GET', url, params=params)