import pytest
from utils.main_api_client import MainAPIClient
from tests import logger

@pytest.fixture
def api_client():
    try:
        return MainAPIClient()
    except Exception as e:
        logger.error(f"初始化客户端时发生错误: {e}")

# def test_add_product_to_cart(api_client):
#     product_id = 1  # 根据实际情况修改
#     response = api_client.cart.add_product_to_cart(product_id)
#     assert response.status_code in [200, 201]

def test_get_cart_list(api_client):
    response = api_client.cart.get_cart_list()
    logger.info(f"获取购物车列表响应: {response.json()}")
    assert response.status_code == 200