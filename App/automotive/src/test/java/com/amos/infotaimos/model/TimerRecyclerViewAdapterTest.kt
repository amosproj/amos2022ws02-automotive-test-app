package com.amos.infotaimos.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amos.infotaimos.R
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class TimerRecyclerViewAdapterTest {

    private lateinit var context: Context
    private lateinit var timerItems: List<TimerItem>
    private lateinit var timerRecyclerViewAdapter: TimerRecyclerViewAdapter

    @Before
    fun setUp() {
        context = mockk()
        timerItems = mockk()
        timerRecyclerViewAdapter = TimerRecyclerViewAdapter(context, timerItems)
        every { timerItems.size } returns 1
    }

    @Test
    fun onCreateViewHolder() {
        val parent = mockk<ViewGroup>()
        mockkStatic(LayoutInflater::class)
        val viewType = 0
        val inflater = mockk<LayoutInflater>()
        val view = mockk<View>()
        every { LayoutInflater.from(context) } returns inflater
        every { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) } returns inflater
        every { inflater.inflate(R.layout.timer_list_item, parent, false) } returns view
        every { view.findViewById<TextView>(R.id.time) } returns mockk()
        every { view.findViewById<TextView>(R.id.actionId_and_duration) } returns mockk()
        every { view.findViewById<TextView>(R.id.description) } returns mockk()
        every { view.findViewById<TextView>(R.id.actionId_and_duration) } returns mockk()
        every { view.parent } returns parent
        assertEquals(view, timerRecyclerViewAdapter.onCreateViewHolder(parent, viewType).itemView)
    }

    @Test
    fun onBindViewHolder() {
        val view = mockk<View>()
        val tvTime = mockk<TextView>()
        val tvDescription = mockk<TextView>()
        val tvActionId = mockk<TextView>()
        every { tvTime.text } returns "time"
        every { tvDescription.text } returns "description"
        every { tvActionId.text } returns "actionId - duration"
        every { tvTime.text = any() } just Runs
        every { tvDescription.text = any() } just Runs
        every { tvActionId.text = any() } just Runs
        every { view.findViewById<TextView>(R.id.time) } returns tvTime
        every { view.findViewById<TextView>(R.id.description) } returns tvDescription
        every { view.findViewById<TextView>(R.id.actionId_and_duration) } returns tvActionId
        val holder = TimerRecyclerViewAdapter.TimerItemViewHolder(view)
        val position = 0
        val timerItem = mockk<TimerItem>()
        every { timerItems[any()] } returns timerItem
        every { timerItem.actionIdAndDuration } returns "actionId - duration"
        every { timerItem.time } returns "time"
        every { timerItem.description } returns "description"
        timerRecyclerViewAdapter.onBindViewHolder(holder, position)
        assertEquals("actionId - duration", holder.actionIdAndDuration.text)
        assertEquals("time", holder.time.text)
        assertEquals("description", holder.description.text)
    }

    @Test
    fun getItemCount() {
        val actual = timerRecyclerViewAdapter.itemCount
        val expected = 1
        assertEquals(expected, actual)
    }
}
