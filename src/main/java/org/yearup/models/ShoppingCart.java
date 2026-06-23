package org.yearup.models;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.Math.round;

public class ShoppingCart
{
    private Map<Integer, ShoppingCartItem> items = new HashMap<>();

    public Map<Integer, ShoppingCartItem> getItems()
    {
        return items;
    }

    public void setItems(Map<Integer, ShoppingCartItem> items)
    {
        this.items = items;
    }

    public boolean contains(int productId)
    {
        return items.containsKey(productId);
    }

    public void add(ShoppingCartItem item)
    {
        items.put(item.getProductId(), item);
    }

    public ShoppingCartItem get(int productId)
    {
        return items.get(productId);
    }

    public double getTotal()
    {

        double total = items.values()
                            .stream()
                            .mapToDouble(ShoppingCartItem::getLineTotal)
                            .sum();



        return total;
    }

}
