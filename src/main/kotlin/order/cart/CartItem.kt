package order.cart

import order.constants.Constants.Companion.APPLE
import order.constants.Constants.Companion.APPLE_MAX_LIMIT
import order.constants.Constants.Companion.ORANGE
import order.constants.Constants.Companion.ORANGE_MAX_LIMIT
import order.domain.Product
import order.exceptions.OutOfStockException
import java.math.BigDecimal

class CartItem @JvmOverloads constructor(var product: Product?, var quantity: Int = 1) {


    fun getQuantity(): Int? {

        return quantity
    }
    @Throws(OutOfStockException::class)
    fun addOne(): Int {
        println(product?.productCode)
        if (product?.productCode.equals(APPLE) && (quantity + 1 < APPLE_MAX_LIMIT))
            return quantity++
        else if (product?.productCode.equals(ORANGE) && (quantity + 1 < ORANGE_MAX_LIMIT))
            return quantity++
        else
            println("Out of Stock")
            throw OutOfStockException("Out of Stock")


    }

    fun reduceOne(): Int {
        return quantity - 1.also { quantity = it }
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (product?.hashCode() ?: 0)
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as CartItem
        if (product == null) {
            if (other.product != null) return false
        } else if (product != other.product) return false
        return true
    }

    fun getLineItemTotalBeforeDiscount(): BigDecimal? {
        return product!!.price!!.unitPrice!!.multiply(BigDecimal(quantity))
    }


}