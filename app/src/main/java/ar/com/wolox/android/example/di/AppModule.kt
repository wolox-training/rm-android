package ar.com.wolox.android.example.di

import ar.com.wolox.android.example.ui.example.ExampleActivity
import ar.com.wolox.android.example.ui.example.ExampleFragment
import ar.com.wolox.android.example.ui.login.LoginActivity
import ar.com.wolox.android.example.ui.login.LoginFragment
import ar.com.wolox.android.example.ui.home.HomePageActivity
import ar.com.wolox.android.example.ui.home.HomePageFragment
import ar.com.wolox.android.example.ui.home.news.NewsFragment
import ar.com.wolox.android.example.ui.newdetail.fullscreen.FullScreenPictureDialog
import ar.com.wolox.android.example.ui.home.profile.ProfileFragment
import ar.com.wolox.android.example.ui.newdetail.NewDetailActivity
import ar.com.wolox.android.example.ui.newdetail.NewDetailFragment
import ar.com.wolox.android.example.ui.root.RootActivity
import ar.com.wolox.android.example.ui.signup.SignUpActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ContributesAndroidInjector
    internal abstract fun exampleActivity(): ExampleActivity

    @ContributesAndroidInjector
    internal abstract fun exampleFragment(): ExampleFragment

    @ContributesAndroidInjector
    internal abstract fun rootActivity(): RootActivity

    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun signupActivity(): SignUpActivity

    @ContributesAndroidInjector
    internal abstract fun homepageActivity(): HomePageActivity

    @ContributesAndroidInjector
    internal abstract fun homepageFragment(): HomePageFragment

    @ContributesAndroidInjector
    internal abstract fun newsFragment(): NewsFragment

    @ContributesAndroidInjector
    internal abstract fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector
    internal abstract fun newDetailActivity(): NewDetailActivity

    @ContributesAndroidInjector
    internal abstract fun newDetailFragment(): NewDetailFragment

    @ContributesAndroidInjector
    internal abstract fun newFullScreenPictureDialog(): FullScreenPictureDialog
}
