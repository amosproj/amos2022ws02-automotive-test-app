package com.amos.infotaimos.model

import android.car.Car
import android.content.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Test

internal class CarInstanceManagerTest {

    @Test
    fun getCarAlwaysReturnsSameInstance() {
        mockkStatic(Car::class)
        val context = mockk<Context>()
        val applicationContext = mockk<Context>()
        every { context.applicationContext } returns applicationContext
        every { Car.createCar(applicationContext) } returnsMany listOf(mockk(), mockk())
        val carInstance = CarInstanceManager.getCarInstance(context)
        val carInstance2 = CarInstanceManager.getCarInstance(context)
        assert(carInstance === carInstance2)
    }
}
