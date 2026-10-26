package com.example.mycity.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycity.R
import com.example.mycity.data.Category
import com.example.mycity.data.Place
import com.example.mycity.ui.utils.MyCityContentType
import com.example.mycity.ui.utils.MyCityNavigationType

/**
 * Экраны приложения. [Start] на компактных экранах показывает список категорий,
 * на больших — сразу рекомендации выбранной категории.
 */
enum class MyCityScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Recommendations(title = R.string.app_name),
    Detail(title = R.string.app_name)
}

@Composable
fun MyCityApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: MyCityViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()

    val navigationType: MyCityNavigationType
    val contentType: MyCityContentType
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = MyCityNavigationType.STACK_NAVIGATION
            contentType = MyCityContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = MyCityNavigationType.NAVIGATION_RAIL
            contentType = MyCityContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = MyCityNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = MyCityContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = MyCityNavigationType.STACK_NAVIGATION
            contentType = MyCityContentType.LIST_ONLY
        }
    }

    val onCategorySelected: (Category) -> Unit = { category ->
        viewModel.updateCurrentCategory(category)
        if (navigationType == MyCityNavigationType.STACK_NAVIGATION) {
            navController.navigate(MyCityScreen.Recommendations.name)
        } else {
            // Категории лежат в navigation rail / drawer, поэтому возвращаемся к списку.
            navController.popBackStack(MyCityScreen.Start.name, inclusive = false)
        }
    }

    val content: @Composable () -> Unit = {
        MyCityScaffold(
            navController = navController,
            uiState = uiState,
            navigationType = navigationType,
            contentType = contentType,
            onCategoryClick = onCategorySelected,
            onPlaceClick = { place ->
                viewModel.updateCurrentPlace(place)
                if (contentType == MyCityContentType.LIST_ONLY) {
                    navController.navigate(MyCityScreen.Detail.name)
                }
            },
            modifier = modifier
        )
    }

    when (navigationType) {
        MyCityNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {
                        for (category in Category.entries) {
                            NavigationDrawerItem(
                                selected = uiState.currentCategory == category,
                                label = { Text(text = stringResource(category.title)) },
                                icon = {
                                    Icon(
                                        imageVector = category.icon,
                                        contentDescription = null
                                    )
                                },
                                onClick = { onCategorySelected(category) },
                                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                            )
                        }
                    }
                },
                modifier = Modifier.testTag(navigationDrawerContentDescription)
            ) {
                content()
            }
        }

        MyCityNavigationType.NAVIGATION_RAIL -> {
            val navigationRailContentDescription = stringResource(R.string.navigation_rail)
            Row {
                NavigationRail(modifier = Modifier.testTag(navigationRailContentDescription)) {
                    for (category in Category.entries) {
                        NavigationRailItem(
                            selected = uiState.currentCategory == category,
                            onClick = { onCategorySelected(category) },
                            icon = {
                                Icon(
                                    imageVector = category.icon,
                                    contentDescription = stringResource(category.title)
                                )
                            }
                        )
                    }
                }
                content()
            }
        }

        MyCityNavigationType.STACK_NAVIGATION -> content()
    }
}

@Composable
private fun MyCityScaffold(
    navController: NavHostController,
    uiState: MyCityUiState,
    navigationType: MyCityNavigationType,
    contentType: MyCityContentType,
    onCategoryClick: (Category) -> Unit,
    onPlaceClick: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showsDetailOnly = currentRoute == MyCityScreen.Detail.name &&
            contentType == MyCityContentType.LIST_ONLY
    val showsCategories = currentRoute == MyCityScreen.Start.name &&
            navigationType == MyCityNavigationType.STACK_NAVIGATION

    val title = when {
        showsCategories -> stringResource(R.string.app_name)
        showsDetailOnly -> stringResource(uiState.currentPlace.name)
        else -> stringResource(uiState.currentCategory.title)
    }
    val canNavigateBack = navController.previousBackStackEntry != null &&
            contentType == MyCityContentType.LIST_ONLY

    Scaffold(
        topBar = {
            MyCityAppBar(
                title = title,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        val contentPadding = PaddingValues(
            top = innerPadding.calculateTopPadding() + dimensionResource(R.dimen.padding_medium),
            bottom = innerPadding.calculateBottomPadding() + dimensionResource(R.dimen.padding_medium)
        )
        val listPadding = PaddingValues(
            start = dimensionResource(R.dimen.padding_medium),
            end = dimensionResource(R.dimen.padding_medium),
            top = contentPadding.calculateTopPadding(),
            bottom = contentPadding.calculateBottomPadding()
        )
        val placesContent: @Composable () -> Unit = {
            if (contentType == MyCityContentType.LIST_AND_DETAIL) {
                PlaceListAndDetailScreen(
                    places = uiState.places,
                    selectedPlace = uiState.currentPlace,
                    onPlaceClick = onPlaceClick,
                    contentPadding = contentPadding
                )
            } else {
                PlaceListScreen(
                    places = uiState.places,
                    selectedPlace = null,
                    onPlaceClick = onPlaceClick,
                    contentPadding = listPadding
                )
            }
        }

        NavHost(
            navController = navController,
            startDestination = MyCityScreen.Start.name
        ) {
            composable(route = MyCityScreen.Start.name) {
                if (navigationType == MyCityNavigationType.STACK_NAVIGATION) {
                    CategoryListScreen(
                        categories = Category.entries,
                        onCategoryClick = onCategoryClick,
                        contentPadding = listPadding
                    )
                } else {
                    placesContent()
                }
            }
            composable(route = MyCityScreen.Recommendations.name) {
                placesContent()
            }
            composable(route = MyCityScreen.Detail.name) {
                if (contentType == MyCityContentType.LIST_AND_DETAIL) {
                    placesContent()
                } else {
                    PlaceDetailScreen(
                        place = uiState.currentPlace,
                        contentPadding = listPadding
                    )
                }
            }
        }
    }
}

@Composable
private fun MyCityAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}
