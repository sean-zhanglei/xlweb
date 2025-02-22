from .address_api_client import AddressAPIClient
from .product_api_client import ProductAPIClient
from .cart_api_client import CartAPIClient
from .order_api_client import OrderAPIClient
from .coupon_api_client import CouponAPIClient

class MainAPIClient:
    def __init__(self):
        self.address = AddressAPIClient()
        self.product = ProductAPIClient()
        self.cart = CartAPIClient()
        self.order = OrderAPIClient()
        self.coupon = CouponAPIClient()