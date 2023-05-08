package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.exceptions.ConvertPhotoException;

import java.nio.charset.StandardCharsets;

/**
 * Утилитный класс
 */
public final class UtilsService {

    /**
     * Конвертер фото из строки в массив байтов
     *
     * @param photo фото в виде строки
     */
    public static byte[] convertPhotoToByte(String photo) {
        if (photo != null) {
            try {
                return photo.split(",")[1].getBytes(StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new ConvertPhotoException("Загружен некорректный файл", e);
            }
        } else return null;
    }

    /**
     * Конвертер фото из массива байтов в строку
     *
     * @param photo фото в виде массива байтов
     */
    public static String convertPhotoToString(byte[] photo) {
        if (photo != null) {
            try {
                return "data:image/png;base64," + new String(photo, StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new ConvertPhotoException("Ошибка при конвертации фото", e);
            }
        } else return null;
    }

}
