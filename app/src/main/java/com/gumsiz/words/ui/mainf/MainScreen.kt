package com.gumsiz.words.ui.mainf

import android.app.Application
import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gumsiz.words.R
import com.gumsiz.words.data.Word
import com.gumsiz.words.data.db.WordsDatabase
import com.gumsiz.words.data.utils.Resource
import com.gumsiz.words.data.utils.Status
import com.gumsiz.words.ui.Screen
import com.gumsiz.words.ui.theme.WordsTheme
import com.gumsiz.words.ui.theme.primaryLightColor

@Composable
fun MainScreen(navController: NavController) {
    val mainViewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(
            database = WordsDatabase.getInstance(LocalContext.current).WordsDAO,
            application = LocalContext.current.applicationContext as Application
        )
    )
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
//                            contentPadding = PaddingValues(vertical = 4.dp),
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
            val data by mainViewModel.data.observeAsState()
            val favData by mainViewModel.favoritedata.observeAsState()
            val prepare by mainViewModel.prepare()
                .observeAsState(Resource(status = Status.LOADING, message = null))
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
                when (prepare.status) {
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
                                                    if (unit is ImageVector) Icon(
                                                        unit,
                                                        tint = Color.Black,
                                                        contentDescription = ""
                                                    )
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
                                data?.let { list ->
                                    VerbList(list, navController, mainViewModel::searchInList)
                                }
                            }
                            1 -> {
                                favData?.let { favList -> FavoriteList(favList, navController, mainViewModel::searchInFavList) }
                            }
                        }
                    }
                    Status.ERROR -> {
                        Text(text = "Ups")
                    }
                    Status.LOADING -> {
                        Column(
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
fun FavoriteList(data: List<Word>, navController: NavController, updateList: (String)-> Unit) {
    val searchText = remember { mutableStateOf("") }
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
            value = searchText.value,
            shape = MaterialTheme.shapes.medium.copy(all = CornerSize(16.dp)),
            onValueChange = {
                updateList(it)
                searchText.value = it
            } ,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = primaryLightColor
            ),
            label = { Text(text = "Geben Sie ein Wort ein") }
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
fun VerbList(data: List<Word>, navController: NavController, updateList: (String)-> Unit) {
    val searchText = remember { mutableStateOf("") }
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
            value = searchText.value,
            shape = MaterialTheme.shapes.medium.copy(all = CornerSize(16.dp)),
            onValueChange = {
                updateList(it)
                searchText.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = primaryLightColor
            ),
            label = { Text(text = "Geben Sie ein Wort ein") }
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
fun VerbItemList(data: List<Word>, navController: NavController) {
    LazyColumn(
        Modifier.padding(8.dp)
    ) {
        items(data) {
            Text(
                text = it.name,
                Modifier.clickable {
                    navController.navigate(Screen.DetailScreen.route + "/${it.name}")
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
    }
}