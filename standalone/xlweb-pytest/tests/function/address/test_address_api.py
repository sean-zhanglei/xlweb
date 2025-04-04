import pytest
from utils.main_api_client import MainAPIClient
from tests import logger

@pytest.fixture
def api_client():
    try:
        return MainAPIClient()
    except Exception as e:
        logger.error(f"初始化客户端时发生错误: {e}")

def test_get_default_address(api_client):
    response = api_client.address.get_default_address()
    logger.info(f"获取默认地址的响应: {response.json()}")
    assert response.status_code == 200

# def test_set_default_address(api_client):
#     data = {"id": 1}  # 根据实际情况修改
#     response = api_client.address.set_default_address(data)
#     assert response.status_code in [200, 201]