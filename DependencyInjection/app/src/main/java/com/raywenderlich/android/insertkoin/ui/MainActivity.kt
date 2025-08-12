/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.raywenderlich.android.insertkoin.ui

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.raywenderlich.android.insertkoin.R
import com.raywenderlich.android.insertkoin.model.RepoData
import com.raywenderlich.android.insertkoin.model.RepoEmpty
import com.raywenderlich.android.insertkoin.model.RepoError
import com.raywenderlich.android.insertkoin.model.RepoResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  private val mainViewModel: MainViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    reposRecyclerView.layoutManager = LinearLayoutManager(this)

    mainViewModel?.getRepoLiveData()?.observe(this, Observer<RepoResult> { result ->
      when (result) {
        is RepoData -> {
          val adapter = RepoAdapter(result.repos.sortedBy { it.name })
          reposRecyclerView.adapter = adapter
          showRecyclerView()
        }
        is RepoError -> {
          showEmptyState()
          Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
          println(result.errorMessage)
        }
        is RepoEmpty -> {
          showEmptyState()
        }
      }
    })

    fetchRepos.setOnClickListener {
      mainViewModel?.getRepos(orgName.text.toString())
      fetchRepos.hideKeyboard()
      showLoading()
    }
  }

  private fun showRecyclerView() {
    reposRecyclerView.visibility = View.VISIBLE
    emptyState.visibility = View.INVISIBLE
    loading.visibility = View.INVISIBLE
  }

  private fun showEmptyState() {
    reposRecyclerView.visibility = View.INVISIBLE
    emptyState.visibility = View.VISIBLE
    loading.visibility = View.INVISIBLE
  }

  private fun showLoading() {
    reposRecyclerView.visibility = View.INVISIBLE
    emptyState.visibility = View.INVISIBLE
    loading.visibility = View.VISIBLE
  }
}
