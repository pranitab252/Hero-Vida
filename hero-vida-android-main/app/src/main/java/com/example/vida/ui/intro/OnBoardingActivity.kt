package com.example.vida.ui.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import android.os.*

import android.widget.*
import androidx.viewpager.widget.ViewPager
import com.example.vida.R
import java.util.*

import android.os.Build
import com.example.vida.common.SegmentedProgressDrawable

import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.vida.ui.landing.LandingActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var mContext: AppCompatActivity
    private lateinit var progressBar: ProgressBar
    private lateinit var tvSkip: TextView
    private lateinit var rlLogin: RelativeLayout
    private val aryOnboarding = arrayListOf<IntroItem>()
    private var mAdapter: PagerAdapter? = null
    private var screenPager: ViewPager? = null
    private var tabIndicator: DotsIndicator? = null
    private lateinit var btnGetStarted: TextView
    private var progressCount: Int = 25

    //declare key
    private var SelectFirst: Boolean? = true
    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
    //private val mViewModel: AuthViewModel by viewModel()


    private fun showProgressDialog() {
        /*tvSkip.isClickable = false
        tvSkip.isEnabled = false
        btnGetStarted.isClickable = false
        btnGetStarted.isEnabled = false
        rlLogin.isClickable = false
        rlLogin.isEnabled = false

        progressBar.visibility = View.VISIBLE
        if (!progressBar.isAnimating) progressBar.playAnimation()*/
    }

    private fun dismissProgressDialog() {
        /*if (progressBar.isAnimating)
            progressBar.pauseAnimation()

        progressBar.visibility = View.GONE
        tvSkip.isClickable = true
        tvSkip.isEnabled = true
        btnGetStarted.isClickable = true
        btnGetStarted.isEnabled = true
        rlLogin.isClickable = true
        rlLogin.isEnabled = true*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        mContext = this
        //MobileAds.openAdInspector(AppController.instance) {}
        attachViewModel()
        initializeView()
        //NativeAdsManager.getInstance().loadNativeAds(AppController.instance.applicationContext,null)
        //AdsUtils.sharedInstance().loadRewardedAd()

    }

    private fun attachViewModel() {
        //mViewModel.attachView(this, this)
    }

    private fun initializeView() {
        btnGetStarted = findViewById(R.id.btn_get_started)
        screenPager = findViewById(R.id.screen_viewpager)
        tabIndicator = findViewById(R.id.tab_indicator)
        progressBar = findViewById(R.id.progressBar)
        tvSkip = findViewById(R.id.tvSkip)
        rlLogin = findViewById(R.id.rlLogin)

        val fillColor = ContextCompat.getColor(mContext, R.color.colorDarkGray)
        val emptyColor = ContextCompat.getColor(mContext, R.color.colorInputGray50)
        val separatorColor = ContextCompat.getColor(mContext, android.R.color.transparent)

        val progressDrawable = SegmentedProgressDrawable(fillColor, emptyColor)
        progressBar.progressDrawable = progressDrawable

        aryOnboarding.add(
            IntroItem(
                R.drawable.onboarding_1,
                getString(R.string.onboarding_title_1),
                getString(R.string.onboarding_desc_1)
            )
        )
        aryOnboarding.add(
            IntroItem(
                R.drawable.onboarding_2,
                getString(R.string.onboarding_title_2),
                getString(R.string.onboarding_desc_2)
            )
        )
        aryOnboarding.add(
            IntroItem(
                R.drawable.onboarding_3,
                getString(R.string.onboarding_title_3),
                getString(R.string.onboarding_desc_3)
            )
        )
        aryOnboarding.add(
            IntroItem(
                R.drawable.onboarding_4,
                getString(R.string.onboarding_title_4),
                getString(R.string.onboarding_desc_4)
            )
        )

        mAdapter = OnboardingAdapter(this, aryOnboarding)
        screenPager?.offscreenPageLimit = 3
        screenPager?.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        screenPager?.adapter = mAdapter
        tabIndicator?.setViewPager(screenPager!!)

        screenPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (SelectFirst == true && positionOffset == 0f && positionOffsetPixels == 0) {
                    onPageSelected(0)
                    SelectFirst = false
                }
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    1 -> {
                        progressCount = 50
                        setProgressBarPercentage(progressCount)
                        btnGetStarted.text = mContext.getString(R.string.skip)
                    }
                    2 -> {
                        progressCount = 75
                        setProgressBarPercentage(progressCount)
                        btnGetStarted.text = mContext.getString(R.string.skip)
                    }
                    3 -> {
                        progressCount = 100
                        setProgressBarPercentage(progressCount)
                        btnGetStarted.text = mContext.getString(R.string.next)
                    }
                    0 -> {
                        progressCount = 25
                        setProgressBarPercentage(progressCount)
                        btnGetStarted.text = mContext.getString(R.string.skip)
                    }
                }
            }

        })


        tvSkip.setOnClickListener {
            //startFreeVersion(it)
        }

        btnGetStarted.setOnClickListener {
            if (screenPager?.currentItem!! <= 2) {
                gotoLandingScreen()
//                screenPager?.setCurrentItem(getItemOfViewPager(+1), true)
            } else {
                gotoLandingScreen()
            }
        }

        rlLogin.setOnClickListener {

        }
    }

    private fun getItemOfViewPager(i: Int): Int {
        return screenPager!!.currentItem + i
    }

    private fun setProgressBarPercentage(progress: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, true)
        }
    }

    private fun gotoLandingScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@OnBoardingActivity, LandingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
            overridePendingTransition(
                R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_left
            )
        }, 300)
    }

}