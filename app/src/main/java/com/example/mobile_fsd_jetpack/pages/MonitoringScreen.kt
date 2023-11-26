package com.example.mobile_fsd_jetpack.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_fsd_jetpack.ui.theme.AlmostWhite
import com.example.mobile_fsd_jetpack.ui.theme.MobilefsdjetpackTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

@Composable
fun MonitoringScreen(navController: NavController?= null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlmostWhite)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Monitoring Screen",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
    }
}

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit
)

@Composable
fun TabScreen(
    text: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

val tabRowItems = listOf(
    TabRowItem(
        title = "Tab 1",
        screen = { TabScreen(text = "Tab 1") },
    ),
    TabRowItem(
        title = "Tab 2",
        screen = { TabScreen(text = "Tab 2") },
    ),
    TabRowItem(
        title = "Tab 3",
        screen = { TabScreen(text = "Tab 3") },
    )
)


//@Composable
//fun TheLayout() {
//    val pagerState = rememberPagerState(pageCount = { 2 })
//    val coroutineScope = rememberCoroutineScope()
//
//    Column(
//        modifier = Modifier
//            .padding(8.dp)
//    ) {
//        TabRow(
//            selectedTabIndex = pagerState.currentPage,
//            indicator = { tabPositions ->
//                TabRowDefaults.Indicator(
//                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
//                    color = Color.Blue
//                )
//            },
//        ) {
//            tabRowItems.forEachIndexed { index, item ->
//                Tab(
//                    selected = pagerState.currentPage == index,
//                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
//                    text = {
//                        Text(
//                            text = item.title,
//                            maxLines = 2,
//                            overflow = TextOverflow.Ellipsis,
//                        )
//                    }
//                )
//            }
//        }
//        HorizontalPager(
////            count = tabRowItems.size,
//            state = pagerState,
//        ) {
//            tabRowItems[pagerState.currentPage].screen()
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun MonitorPreview() {
    MobilefsdjetpackTheme {
        MonitoringScreen()
    }
}