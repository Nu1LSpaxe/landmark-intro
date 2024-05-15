package com.project.landmark_intro

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.landmark_intro.ui.theme.LandmarkIntroTheme


val imageList = listOf(
    R.drawable.zero_two_01,
    R.drawable.zero_two_02,
    R.drawable.zero_two_03,
    R.drawable.zero_two_04,
    R.drawable.zero_two_05,
    R.drawable.jinx_01,
    R.drawable.jinx_02,
    R.drawable.jinx_03,
    R.drawable.jinx_04,
    R.drawable.jinx_05,
)

val imageText = listOf(
    R.string.zero_two_01,
    R.string.zero_two_02,
    R.string.zero_two_03,
    R.string.zero_two_04,
    R.string.zero_two_05,
    R.string.jinx_01,
    R.string.jinx_02,
    R.string.jinx_03,
    R.string.jinx_04,
    R.string.jinx_05,
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            LandmarkIntroTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
////                    ScrollBoxes()
//                    LazyColumn {
//                        items(imageList.size) { index ->
//                            ImageListItem(index= index)
//                        }
//                    }
//                }
//            }
            MyApp()
        }
    }
}



@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") {
            ItemList(navController)
        }
        composable("details/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            DetailsScreen(
                imageRes = imageList[index],
                descriptionRes = imageText[index],
                navController = navController
            )
        }
    }
}

@Composable
fun ItemList(navController: NavHostController) {

    LazyColumn {
        items(imageText.size) { index ->
            val textResource = imageText[index]
            ListItem(textResource) {
                navController.navigate("details/$index")
            }
        }
    }
}

@Composable
fun ListItem(textResource: Int, onClick: () -> Unit) {
    val text = stringResource(textResource)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(24.dp),
        )
    }
}

@Composable
fun DetailsScreen(imageRes: Int, descriptionRes: Int, navController: NavHostController) {
    val context = LocalContext.current;

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val description = stringResource(descriptionRes)
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null, // Provide appropriate content description
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop // Maintain aspect ratio
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(description)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val latitude = 25.0133
                    val longitude = 121.5406
                    val uri = Uri.parse("geo:$latitude,$longitude")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    context.startActivity(intent)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text="Show Google Maps")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text="Back")
            }
        }
    }
}

//@Composable
//fun ImageListItem(index: Int) {
//    val painter: Painter = painterResource(id = imageList[index])
//    val context = LocalContext.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        Image(
//            painter = painter,
//            contentDescription = null, // Provide appropriate content description
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp), // Adjust height as needed
//            contentScale = ContentScale.Crop
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = context.getString(imageText[index])) // Display image index or any text
//    }
//}

//@Composable
//private fun ScrollBoxes() {
//    Column(
//        modifier = Modifier
//            .background(Color.LightGray)
//            .size(100.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//        repeat(10) { index ->
//            Card(
//                modifier = Modifier
//                    .padding(2.dp)
//                    .fillMaxWidth(),
//                elevation = CardDefaults.cardElevation(
//                    defaultElevation = 8.dp
//                )
//            ) {
//                CardContent(
//                    text = "Item $index",
//                    image = painterResource(imageList[index])
//                )
//            }
//        }
//    }
//}

//@Composable
//private fun CardContent(text: String, image: Painter) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.padding(8.dp)
//    ) {
//        Image(
//            painter = image,
//            contentDescription = null, // Provide appropriate content description
//            modifier = Modifier.size(48.dp)
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        Text(text = text)
//    }
//}