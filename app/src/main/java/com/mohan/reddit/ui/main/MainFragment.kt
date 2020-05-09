package com.mohan.reddit.ui.main

import ApiServiceImpl
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mohan.reddit.GlideApp
import com.mohan.reddit.R
import com.mohan.reddit.data.api.ApiHelper
import com.mohan.reddit.data.model.DataMain
import com.mohan.reddit.ui.main.base.ViewModelFactory
import com.mohan.reddit.utils.Status
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var commentsViewModel: CommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewModel()
        setupObserver()
        // TODO: Use the ViewModel
    }

    private fun setupObserver() {
        viewModel.getPosts().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { posts -> renderUi(posts) }
                    tv_title.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    tv_title.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderUi(users: List<DataMain>) {
        tv_title.setText(users[0].data.children[0].data.title)

        val width = users[0].data.children[0].data.preview?.images?.get(0)?.source?.width ?: -1
        val height = users[0].data.children[0].data.preview?.images?.get(0)?.source?.height ?: -1

        if (width > 0 && height > 0) {
            context?.let {
                GlideApp.with(it)
                    .load(users[0].data.children[0].data.url)
                    .placeholder(createPlaceholder(width, height))
                    .into(img_url)
            }
        }

        setupObserverComments(users[0].data.children[0].data.permalink);
    }

    private fun setupObserverComments(permalink: String) {
        commentsViewModel.init(permalink)
        commentsViewModel.getComments().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { comments ->
                        renderUiComments(comments)
                    }
                    tv_title.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    tv_title.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderUiComments(comments: List<DataMain>) {
        try {
            tv_comment.setText(comments[1].data.children[1].data.body)
        } catch (e: IndexOutOfBoundsException) {
            tv_comment.setText("No Comments available");
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)

        commentsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(CommentsViewModel::class.java)
    }

    private fun createPlaceholder(srcWidth: Int, srcHeight: Int): BitmapDrawable {
        val metrics = DisplayMetrics()

        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(metrics)

        val deviceWidth = metrics.widthPixels

        val resizeRatio: Float = deviceWidth.toFloat() / srcWidth
        val placeholderWidth = resizeRatio * srcWidth
        val placeholderHeight = resizeRatio * srcHeight

        val bitmap = Bitmap.createBitmap(
            placeholderWidth.toInt(),
            placeholderHeight.toInt(),
            Bitmap.Config.ARGB_8888
        )

        bitmap.eraseColor(android.graphics.Color.GRAY)

        return BitmapDrawable(context?.resources, bitmap)
    }

}
