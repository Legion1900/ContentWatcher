package com.legion1900.game_list_feature.providers

import java.util.Locale

/**
 * Params:
 * 1st string - resolution enum value;
 * 2nd string - image id;
 */
private const val IMAGE_URL_TEMPLATE = "https://images.igdb.com/igdb/image/upload/%s/%s.jpg"

internal class IGDBUrlFactory {

    fun newArtworkUrl(imgId: String): String = IMAGE_URL_TEMPLATE.format(Locale.ENGLISH, Size.FULL_HD.serviceValue, imgId)

    fun newScreenshotUrl(imgId: String): String = IMAGE_URL_TEMPLATE.format(Locale.ENGLISH, Size.FULL_HD.serviceValue, imgId)

    fun newCoverUrl(imgId: String): String = IMAGE_URL_TEMPLATE.format(Locale.ENGLISH, Size.SCREENSHOT_MED.serviceValue, imgId)

    /**
     * [See details here.](https://api-docs.igdb.com/#images)
     */
    private enum class Size(val serviceValue: String) {
        SCREENSHOT_MED("screenshot_med"),
        HD_READY("720p"),
        FULL_HD("1080p");

        val retina: String by lazy { "${serviceValue}_2x" }
    }
}
