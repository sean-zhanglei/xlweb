import pytest
from utils.main_api_client import MainAPIClient
from tests import logger

@pytest.fixture
def api_client():
    try:
        return MainAPIClient()
    except Exception as e:
        logger.error(f"初始化客户端时发生错误: {e}")

# def test_create_order(api_client):
#     data = {"product_ids": [1, 2]}  # 根据实际情况修改
#     response = api_client.order.create_order(data)
#     assert response.status_code in [200, 201]

def test_get_order_list(api_client):
    response = api_client.order.get_order_list()
    logger.info(f"获取订单列表响应: {response.json()}")
    assert response.status_code == 200