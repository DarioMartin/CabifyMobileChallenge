package com.example.cabifymobilechallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cabifymobilechallenge.presentation.composable.CheckoutView
import com.example.cabifymobilechallenge.presentation.composable.ProductListView
import com.example.cabifymobilechallenge.presentation.theme.CabifyMobileChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val PRODUCT_LIST = "product_list"
        private const val CHECKOUT = "checkout"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CabifyMobileChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = PRODUCT_LIST) {
                        composable(PRODUCT_LIST) {
                            ProductListView {
                                navController.navigate(CHECKOUT)
                            }
                        }

                        composable(CHECKOUT) {
                            CheckoutView()
                        }
                    }
                }
            }
        }
    }

}
