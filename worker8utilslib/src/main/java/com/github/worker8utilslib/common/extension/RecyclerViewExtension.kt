package com.github.worker8utilslib.common.extension

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

private val RecyclerView.onBottomDetectedSubject: PublishSubject<Unit> by lazy { PublishSubject.create<Unit>() }

private fun RecyclerView.makeBottomDetectionScrollListener() = object : RecyclerView.OnScrollListener() {
    var lastItemPositionStore = 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        (layoutManager as LinearLayoutManager).apply {
            if (lastItemPositionStore != findLastVisibleItemPosition()) {
                lastItemPositionStore = findLastVisibleItemPosition()
                if (findLastVisibleItemPosition() + 1 == itemCount) {
                    onBottomDetectedSubject.onNext(Unit)
                }
            }
        }
    }
}

val RecyclerView.onBottomDetectedObservable: Observable<Unit>
    get() = onBottomDetectedSubject.hide()

fun RecyclerView.initBottomDetectListener() {
    addOnScrollListener(makeBottomDetectionScrollListener())
}
