# 添加一些全局的初始化代码或者配置测试环境的代码
import os

# 可以在这里设置环境变量等操作
os.environ['TEST_ENV'] = 'true'

# 也可以添加日志配置等
import logging

logging.basicConfig(level=logging.INFO)

# 创建日志记录器
logger = logging.getLogger(__name__)
# 设置日志级别
logger.setLevel(logging.INFO)

# 创建控制台处理器
console_handler = logging.StreamHandler()
console_handler.setLevel(logging.INFO)

# 创建日志格式
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
console_handler.setFormatter(formatter)

# 将处理器添加到日志记录器
logger.addHandler(console_handler)
logger.info("Tests module initialized.")