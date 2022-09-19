package com.example.cabifymobilechallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cabifymobilechallenge.R
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
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    Scaffold(
                        topBar = { ToolBar(navBackStackEntry, navController) }
                    ) {

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

    @Composable
    private fun ToolBar(
        navBackStackEntry: NavBackStackEntry?,
        navController: NavHostController
    ) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            navigationIcon = {
                if (navBackStackEntry?.destination?.route != PRODUCT_LIST) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }

}
