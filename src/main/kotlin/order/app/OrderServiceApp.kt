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


object OrderServiceApp {

    private val basket = Basket()
    private val myProducer = CustomerNotificationProducer()
    private val orderServiceProducer = order.producer.OrderServiceProducer()

    @JvmStatic
    fun main(args: Array<String>) {

        println("OrderServiceApp Started.")

        val orderedItems = Arrays.asList(*args)

        println("orderedItems::${orderedItems}")

        println("Current Offers::${basket.offerMap}")

        println("adding items to basket...")
        try {
            addToCart(orderedItems)
            val totalPriceBeforeDiscount = NumberFormat.getCurrencyInstance().format(basket.totalPriceBeforeDiscount)
            val totalPriceAfterDiscount = NumberFormat.getCurrencyInstance().format(basket.totalPriceAfterDiscount)
            val totalDiscount = NumberFormat.getCurrencyInstance().format(basket.totalDiscount)
            println("basket no.of products::${basket.totalLineItem}")
            println("totalPriceBeforeDiscount::${totalPriceBeforeDiscount}")
            println("totalPriceAfterDiscount::${totalPriceAfterDiscount}")
            println("totalDiscount::${totalDiscount}")

            myProducer.sendMessage("****************************************************Order Completed")
            println("****************************************************Order Completed")
        } catch (e: OutOfStockException) {
            e.printStackTrace()
            myProducer.sendMessage("****************************************************Order Failed: Out of stock")
            println("****************************************************Order Failed: Out of stock")
        }

        //orderServiceProducer.produceMessage(basket.toString())

        println("OrderServiceApp Completed.")
    }

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

    }
}