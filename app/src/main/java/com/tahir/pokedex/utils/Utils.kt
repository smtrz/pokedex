package com.tahir.pokedex.utils

import com.tahir.pokedex.constants.AppConstants.Companion.INITIAL_BACKOFF

object Utils {
    fun getBackoffDelay(attempt: Long) = INITIAL_BACKOFF * (attempt + 1)

}