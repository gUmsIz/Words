package com.gumsiz.words.ui.mainf

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.gumsiz.shared.data.model.WordModel
import com.gumsiz.words.R
import com.gumsiz.words.utils.Status
import com.gumsiz.words.ui.Screen
import com.gumsiz.words.ui.theme.WordsTheme
import com.gumsiz.words.ui.theme.primaryLightColor
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    val mainViewModel = koinViewModel<MainViewModel>()
    val openDialog = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Verben", color = Color.Black) },
                actions = {
                    val dropdown = remember {
                        mutableStateOf(false)
                    }
                    IconButton(
                        onClick = { dropdown.value = true },
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            tint = Color.Black,
                            contentDescription = ""
                        )
                    }
                    DropdownMenu(
                        expanded = dropdown.value,
                        onDismissRequest = { dropdown.value = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                dropdown.value = false
                                openDialog.value = true
                            }
                        ) {
                            Text(text = stringResource(id = R.string.btn_menu_about))
                        }
                    }
                },
            )
        },
        content = {
            var state by remember { mutableStateOf(0) }
            val newData: List<WordModel?> by mainViewModel.allVerbsList.collectAsState(initial = emptyList())
            val newFavData: List<WordModel?> by mainViewModel.favoriteVerbsList.collectAsState(initial = emptyList())
            val verbData by mainViewModel.dataStateFlow.collectAsState()
            val titles = listOf(
                "Verben",
                Icons.Default.Favorite
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (openDialog.value) InfoDialog { openDialog.value = false }
                when (verbData.status) {
                    Status.SUCCESS -> {
                        TabRow(
                            selectedTabIndex = state,
                            tabs = {
                                titles.forEachIndexed { index, unit ->
                                    when (unit) {
                                        is String -> {
                                            Tab(
                                                text = { Text(text = unit, color = Color.Black) },
                                                selected = state == index,
                                                onClick = { state = index })
                                        }

                                        else -> {
                                            Tab(
                                                icon = {
                                                    if (unit is ImageVector) Crossfade(targetState = state == index,
                                                        label = ""
                                                    ) { state->
                                                        if (state){
                                                            Icon(
                                                                unit,
                                                                tint = Color.Black,
                                                                contentDescription = ""
                                                            )
                                                        }else{
                                                            Icon(
                                                                Icons.Default.FavoriteBorder,
                                                                tint = Color.Black,
                                                                contentDescription = ""
                                                            )
                                                        }
                                                    }
                                                },
                                                selected = state == index,
                                                onClick = { state = index })
                                        }
                                    }
                                }
                            }
                        )
                        when (state) {
                            0 -> {
                                VerbList(
                                    newData,
                                    navController,
                                    mainViewModel::searchInList,
                                    mainViewModel
                                )
                            }

                            1 -> {
                                FavoriteList(
                                    newFavData,
                                    navController,
                                    mainViewModel::searchInFavList,
                                    mainViewModel
                                )
                            }
                        }
                    }

                    Status.ERROR -> {
                        Text(text = "Ups")
                    }

                    Status.LOADING -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Card(backgroundColor = MaterialTheme.colors.secondary) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.load_message),
                                        Modifier.padding(8.dp)
                                    )
                                    CircularProgressIndicator(Modifier.padding(vertical = 8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun FavoriteList(
    data: List<WordModel?>,
    navController: NavController,
    updateList: (String) -> Unit,
    mainViewModel: MainViewModel
) {
    val searchText by mainViewModel.searchQueryInFavorite.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
    ) {
        TextField(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray, MaterialTheme.shapes.medium.copy(CornerSize(16.dp)))
                .align(alignment = Alignment.CenterHorizontally),
            value = searchText,
            shape = MaterialTheme.shapes.medium.copy(all = CornerSize(16.dp)),
            onValueChange = {
                updateList(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = primaryLightColor
            ),
            label = { Text(text = "Geben Sie ein Wort ein") },
            singleLine = true
        )
        Card(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxSize(),
            shape = MaterialTheme.shapes.medium.copy(all = CornerSize(16.dp)),
            backgroundColor = primaryLightColor,
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            VerbItemList(data = data, navController)
        }
    }
}

@Composable
fun VerbList(
    data: List<WordModel?>,
    navController: NavController,
    updateList: (String) -> Unit,
    mainViewModel: MainViewModel
) {
    val searchText by mainViewModel.searchQuery.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
    ) {
        TextField(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray, MaterialTheme.shapes.medium.copy(CornerSize(16.dp)))
                .align(alignment = Alignment.CenterHorizontally),
            value = searchText,
            shape = MaterialTheme.shapes.medium.copy(all = CornerSize(16.dp)),
            onValueChange = {
                updateList(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = primaryLightColor
            ),
            label = { Text(text = "Geben Sie ein Wort ein") },
            singleLine = true
        )
        Card(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxSize(),
            shape = MaterialTheme.shapes.medium.copy(all = CornerSize(16.dp)),
            backgroundColor = primaryLightColor,
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            VerbItemList(data = data, navController)
        }
    }
}

@Composable
fun VerbItemList(data: List<WordModel?>, navController: NavController) {
    LazyColumn(
        Modifier.padding(8.dp)
    ) {
        items(data) {
            Text(
                text = it?.name ?: "",
                Modifier.clickable {
                    navController.navigate(Screen.DetailScreen.route + "/${it?.name}")
                }
            )
        }
    }
}

@Composable
fun InfoDialog(dismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = dismiss,
        text = {
            Column {
                Text(text = stringResource(id = R.string.txt_about))
            }
        },
        buttons = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = dismiss) {
                    Text(text = "OK", color = Color.Black)
                }
                TextButton(onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.d-seite.de/vis/hinweise.html")
                    )
                    startActivity(context, intent, null)
                }) {
                    Text(text = "d-seite.de", color = Color.Black)
                }
            }
        }
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    WordsTheme {
//        MainScreen()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Card(backgroundColor = MaterialTheme.colors.secondary) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.load_message),
                        Modifier.padding(8.dp)
                    )
                    CircularProgressIndicator(Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}