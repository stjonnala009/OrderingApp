package order.app

import order.cart.Basket
import order.cart.CartItem
import order.constants.Constants
import order.domain.Price
import order.domain.Product
import order.exceptions.OutOfStockException
import order.kafka.CustomerNotificationProducer
import java.text.NumberFormat
import java.util.*
import kotlin.system.exitProcess


object OrderServiceApp {

    private val basket = Basket()
    private val myProducer = CustomerNotificationProducer()

    @JvmStatic
    fun main(args: Array<String>) {

        if (args.size <= 2) {
            println("Please provide command line arguments")
            exitProcess(1)
        }

        println("OrderServiceApp Started.")

        val orderedItems = Arrays.asList(*args)

        println("orderedItems::${orderedItems}")
        basket.offerMap = Collections.emptyMap()
        var producer = myProducer.createProducer()

        try {

            addToCart(orderedItems)

            val totalPriceBeforeDiscount = NumberFormat.getCurrencyInstance().format(basket.totalPriceBeforeDiscount)


            println("totalPrice::${totalPriceBeforeDiscount}")

            println("Order Successfully Placed")

            myProducer.sendMessage("Order Successfully Placed", producer)


        } catch (e: OutOfStockException) {
            myProducer.sendMessage("Order Failed: Out of stock", producer)
            println("Order Failed: Out of stock")
        } catch (e: Exception) {
            myProducer.sendMessage("Order Failed: Internal error", producer)
            println("Order Failed: Internal error")
        } finally {
            producer.close()
            println("OrderServiceApp Completed.")
        }


    }

    /**
     * Adds cartItem to the cart.
     * @param [orderedItems] to this cart
     * @return void.
     */
    @Throws(OutOfStockException::class)
    fun addToCart(orderedItems: List<String>) {


        for (lineItem in orderedItems) {
            when (lineItem) {
                "Apple" -> {
                    basket.addCartItem(CartItem(Product(Constants.APPLE, Price(Constants.APPLE_COST)), 1))
                }
                "Orange" -> {
                    basket.addCartItem(CartItem(Product(Constants.ORANGE, Price(Constants.ORANGE_COST)), 1))
                }
            }
        }
        basket.refreshTotalDiscount()

    }
}