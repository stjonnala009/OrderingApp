package order.app

import order.cart.Basket
import order.cart.CartItem
import order.constants.Constants
import order.constants.DiscountType
import order.domain.Offer
import order.domain.Price
import order.domain.Product
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*


class OrderServiceAppTest {
    private var basket = Basket()

    val price_5: Price = Price(BigDecimal(0.60))
    val price_10: Price = Price(BigDecimal(0.25))
    val orange: Product = Product("Orange", price_10)
    val apple: Product = Product("Apple", price_5)
    var offerMap: MutableMap<String?, Offer?> = HashMap()
    val Item_1 = CartItem(apple, 1)
    val Item_2 = CartItem(apple, 1)
    val Item_4 = CartItem(orange, 1)
    val Item_3 = CartItem(apple, 1)


    fun setup() {
        basket = Basket()
        val appleProduct = Product(Constants.APPLE, Price(BigDecimal(0.60)))

        val appleOffer = Offer(appleProduct, 1, DiscountType.TWO_FOR_ONE.discount)

        offerMap.put(Constants.APPLE, appleOffer)

        //initialize orange and add offer
        val orangeProduct = Product(Constants.ORANGE, Price(BigDecimal(0.25)))
        val orangeOffer = Offer(orangeProduct, 1, DiscountType.THREE_FOR_TWO.discount)
        offerMap.put(Constants.ORANGE, orangeOffer)
    }

    @Test
    fun testAddMultipleQuantityofProductWithRemovingOneUnit() {
        setup()

        basket.addCartItem(Item_1)
        basket.addCartItem(Item_2)
        basket.addCartItem(Item_3)
        basket.addCartItem(Item_4)

        Assert.assertTrue(2 == basket.totalLineItem)
        Assert.assertTrue(3 == basket.cartItems.get(0).getQuantity())

        basket.decreaseByOne(Item_4)
        Assert.assertTrue(1 == basket.totalLineItem)
        Assert.assertTrue(3 == basket.cartItems.get(0).getQuantity())
    }

    @Test
    fun testAddApplesVerifyTotalWithoutOffer() {
        setup()
        //3 apples
        basket.addCartItem(Item_1)
        basket.addCartItem(Item_2)
        basket.addCartItem(Item_3)

        //remove offer
        basket.offerMap = Collections.emptyMap()

        Assert.assertTrue(1 == basket.totalLineItem)
        basket.refreshTotalDiscount()
        val totalPrice = NumberFormat.getCurrencyInstance().format(basket.totalPriceBeforeDiscount)

        val totalPriceAfterDiscount = NumberFormat.getCurrencyInstance().format(basket.totalPriceAfterDiscount)

        Assert.assertTrue("$1.80" == totalPrice)
        Assert.assertTrue("$1.80" == totalPriceAfterDiscount)
    }

    @Test
    fun testAddOrangesVerifyTotalWithoutOffer() {
        setup()
        //3 oranges
        basket.addCartItem(Item_4)
        basket.addCartItem(Item_4)
        basket.addCartItem(Item_4)

        //remove offer
        basket.offerMap = Collections.emptyMap()

        Assert.assertTrue(1 == basket.totalLineItem)
        basket.refreshTotalDiscount()
        val totalPrice = NumberFormat.getCurrencyInstance().format(basket.totalPriceBeforeDiscount)

        val totalPriceAfterDiscount = NumberFormat.getCurrencyInstance().format(basket.totalPriceAfterDiscount)

        Assert.assertTrue("$0.75" == totalPrice)
        Assert.assertTrue("$0.75" == totalPriceAfterDiscount)
    }


}