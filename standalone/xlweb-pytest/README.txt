# 安装依赖
ip install -r requirements.txt

# 功能测试
pytest tests/test_api_functional.py

# 性能测试
locust -f tests/test_api_performance.py