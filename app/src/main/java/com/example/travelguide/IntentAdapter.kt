package com.example.travelguide

import android.app.Activity
import android.content.Context
import android.content.Intent

object IntentAdapter {

    fun openCreatePostPage(context: Context) {
        val intent = Intent(context, PostCreatePage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openProfilePage(context: Context) {
        val intent = Intent(context, ProfilePage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openCategoriesPage(context: Context) {
        val intent = Intent(context, CategoriesPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openHomePage(context: Context) {
        val intent = Intent(context, HomePage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openHistoricalSitesPage(context: Context) {
        val intent = Intent(context, HistoricalSitesPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openLodgingPage(context: Context) {
        val intent = Intent(context, LodgingPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openGastronomyPage(context: Context) {
        val intent = Intent(context, GastronomyPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openShoppingSpotsPage(context: Context) {
        val intent = Intent(context, ShoppingSpotsPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openCulturalEventsPage(context: Context) {
        val intent = Intent(context, CulturalEventsPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openOutdoorActivitiesPage(context: Context) {
        val intent = Intent(context, OutdoorActivitiesPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openTransportationPage(context: Context) {
        val intent = Intent(context, TransportationPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun backToLoginPage(context: Context) {
        val intent = Intent(context, LoginPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun openSignupPage(context: Context) {
        val intent= Intent(context, SignUpPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }
    fun openForgotPasswordPage(context: Context) {
        val intent= Intent(context, ForgotPasswordPage::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }
}