package ru.clevertec.check.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLCommands {
    public static String DISCOUNT_BY_NUMBER = "SELECT * FROM public.discount_card WHERE number = ?";
    public static String PRODUCT_BY_ID = "SELECT * FROM public.product WHERE id = ?";
    public static String DISCOUNT_CARD_BY_ID = "SELECT * FROM public.discount_card WHERE id = ?";
    public static String SAVE_PRODUCT = "INSERT INTO public.product (description, price, quantity_in_stock, wholesale_product) VALUES (?,?,?,?)";
    public static String DELETE_PRODUCT_BY_ID = "DELETE FROM public.product as p WHERE p.id = ?";
    public static String FULL_PRODUCT_UPDATE = "UPDATE public.product SET description = ?, price = ?, quantity_in_stock = ?, wholesale_product = ? WHERE id = ?";
    public static String SAVE_DISCOUNT_CARD = "INSERT INTO public.discount_card (number, amount) VALUES (?,?)";
    public static String DELETE_DISCOUNT_CAR_BY_ID = "DELETE FROM public.discount_card as dc WHERE dc.id = ?";
    public static String FULL_UPDATE_DISCOUNT_CARD = "UPDATE public.discount_card SET number = ?, amount = ? WHERE id = ?";
}
