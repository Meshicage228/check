package ru.clevertec.check.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLCommands {
    public static String DISCOUNT_BY_NUMBER = "SELECT * FROM public.discount_card WHERE number = ?";
    public static String PRODUCT_BY_ID = "SELECT * FROM public.product WHERE id = ?";
}
