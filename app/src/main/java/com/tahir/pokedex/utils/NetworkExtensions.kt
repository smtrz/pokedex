package com.tahir.pokedex.utils

import com.tahir.pokedex.constants.AppConstants
import com.tahir.pokedex.generics.ResponseResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import okio.IOException

/**
 * You may want to apply some common side-effects to your flow to avoid repeating commonly used
 * logic across your app.
 *
 * For e.g. If you want to show/hide progress then use side-effect methods like
 * onStart & onCompletion
 *
 * You can also write common business logic which is applicable to all flows in your application,
 * in this case we are retrying requests 3 times with an exponential delay; if the exception thrown
 * is of type IOException.
 *
 */
fun <T : Any> Flow<ResponseResult<T>>.applyCommonSideEffects() =
		retryWhen { cause, attempt ->
			when {
				(cause is IOException && attempt < AppConstants.MAX_RETRIES) -> {
					delay(Utils.getBackoffDelay(attempt))
					true
				}
				else -> {
					false
				}
			}
		}
				.onStart { emit(ResponseResult.Progress(isLoading = true)) }
				.onCompletion { emit(ResponseResult.Progress(isLoading = false)) }

fun Job?.cancelIfActive() {
	if (this?.isActive == true) {
		cancel()
	}
}
