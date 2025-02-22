import pytest
from utils.main_api_client import MainAPIClient
from tests import logger

@pytest.fixture
def api_client():
    try:
        return MainAPIClient()
    except Exception as e:
        logger.error(f"初始化客户端时发生错误: {e}")

def test_get_coupon_list(api_client):
    response = api_client.coupon.get_coupon_list()
    logger.info(f"获取优惠券列表响应: {response.json()}")
    assert response.status_code == 200

# def test_receive_coupon(api_client):
#     coupon_id = 1  # 根据实际情况修改
#     response = api_client.coupon.receive_coupon(coupon_id)
#     assert response.status_code in [200, 201]