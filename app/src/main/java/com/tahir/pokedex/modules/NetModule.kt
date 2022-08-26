package com.tahir.pokedex.modules

import androidx.multidex.BuildConfig
import com.tahir.pokedex.api.PokemonService
import com.tahir.pokedex.constants.AppConstants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Class responsible for providing the network objects when needed.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetModule {
	@Singleton
	@Provides
	fun provideHttpClient(): OkHttpClient {
		return OkHttpClient.Builder()
				.readTimeout(15, TimeUnit.SECONDS)
				.connectTimeout(15, TimeUnit.SECONDS)
				.build()
	}

	@Singleton
	@Provides
	fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

	@Singleton
	@Provides
	fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
		return Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(getHttpLoggingClient())
				.addConverterFactory(gsonConverterFactory)
				.build()
	}

	fun getHttpLoggingClient(): OkHttpClient {
		val httpLoggingInterceptor = HttpLoggingInterceptor()
		if (BuildConfig.DEBUG) {
			httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
		} else {
			httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
		}
		return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
	}

	@Singleton
	@Provides
	fun provideCurrencyService(retrofit: Retrofit): PokemonService =
			retrofit.create(PokemonService::class.java)
}
