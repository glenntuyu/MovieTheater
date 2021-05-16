package com.example.movietheater.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

fun <T> LiveData<T>.observerOnChangedNotCalled(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): Boolean {
    val latch = CountDownLatch(1)
    val observer = Observer<T> { latch.countDown() }

    this.observeForever(observer)

    return if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        true
    } else {
        this.removeObserver(observer)
        false
    }
}

suspend fun <T : Any> PagingData<T>.collectData(): List<T> {
    val dcb = object : DifferCallback {
        override fun onChanged(position: Int, count: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }
    val items = mutableListOf<T>()
    val dif = object : PagingDataDiffer<T>(dcb, TestCoroutineDispatcher()) {
        override suspend fun presentNewList(
            previousList: NullPaddedList<T>,
            newList: NullPaddedList<T>,
            newCombinedLoadStates: CombinedLoadStates,
            lastAccessedIndex: Int,
            onListPresentable: () -> Unit
        ): Int? {
            for (idx in 0 until newList.size)
                items.add(newList.getFromStorage(idx))
            onListPresentable()
            return null
        }
    }
    dif.collectFrom(this)
    return items
}